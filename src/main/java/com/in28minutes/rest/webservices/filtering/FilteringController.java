package com.in28minutes.rest.webservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	@GetMapping(path="/filtering")
	public MappingJacksonValue getSomeBean() {
		SomeBean someBean = new SomeBean("value1","value2","value3");
		/*Dynamic Filtering Logic*/
		MappingJacksonValue mappingJacksonValue= getFilteredData(someBean,"SomeBeanFilter","field1","field2");
		return mappingJacksonValue;
	}
	
	@GetMapping(path="/filtering-list")
	public MappingJacksonValue getSomeBeanList() {
		List<SomeBean> list = Arrays.asList(new SomeBean("value1","value2","value3"),
				new SomeBean("value4","value5","value6"),
				new SomeBean("value7","value8","value9"));
		
		/*Dynamic Filtering Logic*/
		MappingJacksonValue mappingJacksonValue= getFilteredData(list,"SomeBeanFilter","field1","field2");
		return mappingJacksonValue;
		
		
	}
	
	public MappingJacksonValue getFilteredData(Object filteringBean,String filteringBeanName, String... propertyArray) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(propertyArray);
		FilterProvider filters = new SimpleFilterProvider().addFilter(filteringBeanName, filter);
		MappingJacksonValue mapping = new MappingJacksonValue(filteringBean);
		mapping.setFilters(filters);
		return mapping;
	}
}
