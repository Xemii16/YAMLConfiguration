package com.yecraft.util.io;

import java.io.Serializable;
import java.util.Map;

import com.yecraft.yaml.configuration.serialization.ConfigurationSerializable;
import com.yecraft.yaml.configuration.serialization.ConfigurationSerialization;

import com.google.common.collect.ImmutableMap;

class Wrapper<T extends Map<String, ?> & Serializable> implements Serializable {
    private static final long serialVersionUID = -986209235411767547L;

    final T map;

    static Wrapper<ImmutableMap<String, ?>> newWrapper(ConfigurationSerializable obj) {
        return new Wrapper<ImmutableMap<String, ?>>(ImmutableMap.<String, Object>builder().put(ConfigurationSerialization.SERIALIZED_TYPE_KEY, ConfigurationSerialization.getAlias(obj.getClass())).putAll(obj.serialize()).build());
    }

    private Wrapper(T map) {
        this.map = map;
    }
}
