package com.diogorj.wishlist.mapper;

import lombok.val;
import org.modelmapper.ExpressionMap;
import org.modelmapper.ModelMapper;

import java.util.Arrays;

import static java.util.Objects.nonNull;

public interface Mapper {
    default <T> T map(Object source, Class<T> clazz, ExpressionMap... expressionMap) {
        val mapper = mapper();
        addMappings(mapper, source, clazz, expressionMap);
        return mapper.map(source, clazz);
    }

    default <T> void addMappings(ModelMapper mapper, Object source, Class<T> clazz, ExpressionMap[] expressionMap) {
        if (nonNull(expressionMap) && expressionMap.length > 0) {
            Arrays.stream(expressionMap).forEach(exp -> mapper.typeMap(source.getClass(), clazz).addMappings(exp));
        }
    }

    default ModelMapper mapper() {
        return new ModelMapperConfiguration().modelMapper();
    }

}
