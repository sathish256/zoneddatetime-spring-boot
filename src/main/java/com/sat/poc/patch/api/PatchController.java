package com.sat.poc.patch.api;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/v1/patch")
public class PatchController {

	@Autowired
	PatchMappingProperties patchMappingProperties;

	@PostMapping("/2")
	public ComplexObject getExisitng() {
		return existingObject();
	}

	@PostMapping
	public ResponseEntity<ComplexObject> save(@RequestBody ComplexObject complexObject) throws Exception {
		System.out.println("Request received");

		ComplexObject existingObj = null;
		Object returnObj = null;
		try {

			constructPatchMapping();
			existingObj = existingObject();
			returnObj = copyNonNullProperties(existingObj, complexObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseEntity.ok(ComplexObject.class.cast(returnObj));
	}

	private void constructPatchMapping() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Resource r = new ClassPathResource("patch-mapping.json");
		String mapping = new String(Files.readAllBytes(r.getFile().toPath()));

		patchMappingProperties = mapper.readValue(mapping, PatchMappingProperties.class);
	}

	private void processObject(Field f, Object existingObj, Class<?> type)
			throws IllegalArgumentException, IllegalAccessException {
		if (type.cast(f.get(existingObj)) instanceof List) {
			System.out
					.println("ArrayName :" + f.getType().getName() + "<->" + f.getName() + "<->" + f.get(existingObj));
			List<?> list = (List<?>) type.cast(f.get(existingObj));
			processArray(list);
		} else {
			System.out.println("ObjectName :" + f.getType().getName() + "<->" + f.getName() + "<->"
					+ type.cast(f.get(existingObj)));
		}

	}

	private void processArray(List<?> list) {
		for (Object obj : list) {
			System.out.println(obj.getClass().getSimpleName());
			System.out.println("Print All Fields and Value");
			for (Field f : obj.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				try {
					processObject(f, obj, f.getType());
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@SuppressWarnings("rawtypes")
	public Object copyNonNullProperties(Object target, Object in) throws Exception {
		if (in == null || target == null || target.getClass() != in.getClass())
			return null;

		final BeanWrapper src = new BeanWrapperImpl(in);
		final BeanWrapper trg = new BeanWrapperImpl(target);

		for (final Field property : target.getClass().getDeclaredFields()) {
			Object providedObject = src.getPropertyValue(property.getName());
			Object existingObject = trg.getPropertyValue(property.getName());

			if (providedObject != null && providedObject.getClass().getName().startsWith("com.sat.poc")) {
				trg.setPropertyValue(property.getName(), copyNonNullProperties(existingObject, providedObject));
			} else if (providedObject != null && isCollection(providedObject)) {
				List updated = (List) processArray(existingObject, providedObject,
						Class.forName(getClassName(property.getName()).getClassName()),
						getClassName(property.getName()).getKeys());
				trg.setPropertyValue(property.getName(), updated);
			} else if (providedObject != null) {
				trg.setPropertyValue(property.getName(), providedObject);
			}
		}
		return target;
	}

	public static List<?> convertObjectToList(Object obj) {
		List<?> list = new ArrayList<>();
		if (obj.getClass().isArray()) {
			list = Arrays.asList((Object[]) obj);
		} else if (obj instanceof Collection) {
			list = new ArrayList<>((Collection<?>) obj);
		}
		return list;
	}

	public static boolean isCollection(Object obj) {
		return obj.getClass().isArray() || obj instanceof Collection;
	}

	private PatchMap getClassName(String name) {
		return patchMappingProperties.getMapping().stream().filter(e -> e.getFieldName().equals(name)).findAny()
				.orElse(null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object processArray(Object existingObject, Object providedObject, Class<?> type, String keyFieldName)
			throws Exception {
		System.out.println(
				"Processing Array of type :" + type.getSimpleName() + "--" + providedObject.getClass().getTypeName());
		List existing = convertObjectToList(existingObject);
		List provided = convertObjectToList(providedObject);
		// existing.addAll(provided);
		for (Object obj : provided) {
			String keyValue = (String) new PropertyDescriptor(keyFieldName, type).getReadMethod().invoke(obj);
			Object foundMatch = existing.stream().filter(e -> {
				try {
					return keyValue.equals(new PropertyDescriptor(keyFieldName, type).getReadMethod().invoke(e));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| IntrospectionException e1) {
					e1.printStackTrace();
				}
				return false;
			}).findAny().orElse(null);

			if (foundMatch == null) {
				existing.add(obj);
			} else {

				Object updated = copyNonNullProperties(foundMatch, obj);
				existing.remove(foundMatch);
				existing.add(type.cast(updated));
			}

		}

		return existing;
	}

	public ComplexObject existingObject() {

		ComplexObject obj = new ComplexObject();

		obj.setCreateAt(ZonedDateTime.now());
		obj.setId(123);
		obj.setName("TestFirst");

		SecondaryObject secondObject = new SecondaryObject();
		secondObject.setSecondaryId("1234");
		secondObject.setSecondaryName("SecondaryName");
		List<ThirdLevelObject> thirdLevelObjectArray = new ArrayList<>();
		ThirdLevelObject thirdLevelObject = new ThirdLevelObject();
		thirdLevelObject.setComplexId1("key1");
		thirdLevelObject.setComplexId2("key2");
		thirdLevelObject.setThirdName("ThirdName");
		secondObject.setThirdLevelObject(thirdLevelObject);
		thirdLevelObject = new ThirdLevelObject();
		thirdLevelObject.setComplexId1("key1");
		thirdLevelObject.setComplexId2("key2");
		thirdLevelObject.setThirdName("ThirdName");
		thirdLevelObjectArray.add(thirdLevelObject);
		secondObject.setThirdLevelObjectArray(thirdLevelObjectArray);
		obj.setSecondObject(secondObject);
		List<SecondaryObject> secondaryObjectArray = new ArrayList<>();

		secondObject = new SecondaryObject();
		secondObject.setSecondaryId("4567");
		secondObject.setSecondaryName("SecondaryName");
		secondaryObjectArray.add(secondObject);
		obj.setSecondaryObjectArray(secondaryObjectArray);

		return obj;
	}

}
