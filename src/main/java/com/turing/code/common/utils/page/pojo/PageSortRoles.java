package com.turing.code.common.utils.page.pojo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Turing
 */
public class PageSortRoles extends HashMap<String, Map<String, String>> {

    public PageSortRoles(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public PageSortRoles(int initialCapacity) {
        super(initialCapacity);
    }

    public PageSortRoles() {
        super();
    }

    public PageSortRoles(Map<? extends String, ? extends Map<String, String>> m) {
        super(m);
    }

    /**
     * 添加排序规则
     *
     * @param sortKey   key
     * @param sortField 排序字段
     * @param sortWay   排序方式 (desc, asc)
     */
    public void putPageSortRoles(String sortKey, String sortField, SortWay sortWay) {
        Map<String, String> sortMap = new HashMap<>();
        sortMap.put("field", sortField);
        sortMap.put("way", sortWay.toString());
        this.put(sortKey, sortMap);
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public Map<String, String> get(Object key) {
        return super.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    @Override
    public Map<String, String> put(String key, Map<String, String> value) {
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Map<String, String>> m) {
        super.putAll(m);
    }

    @Override
    public Map<String, String> remove(Object key) {
        return super.remove(key);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public boolean containsValue(Object value) {
        return super.containsValue(value);
    }

    @Override
    public Set<String> keySet() {
        return super.keySet();
    }

    @Override
    public Collection<Map<String, String>> values() {
        return super.values();
    }

    @Override
    public Set<Entry<String, Map<String, String>>> entrySet() {
        return super.entrySet();
    }

    @Override
    public Map<String, String> getOrDefault(Object key, Map<String, String> defaultValue) {
        return super.getOrDefault(key, defaultValue);
    }

    @Override
    public Map<String, String> putIfAbsent(String key, Map<String, String> value) {
        return super.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return super.remove(key, value);
    }

    @Override
    public boolean replace(String key, Map<String, String> oldValue, Map<String, String> newValue) {
        return super.replace(key, oldValue, newValue);
    }

    @Override
    public Map<String, String> replace(String key, Map<String, String> value) {
        return super.replace(key, value);
    }

    @Override
    public Map<String, String> computeIfAbsent(String key, Function<? super String, ? extends Map<String, String>> mappingFunction) {
        return super.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public Map<String, String> computeIfPresent(String key, BiFunction<? super String, ? super Map<String, String>, ? extends Map<String, String>> remappingFunction) {
        return super.computeIfPresent(key, remappingFunction);
    }

    @Override
    public Map<String, String> compute(String key, BiFunction<? super String, ? super Map<String, String>, ? extends Map<String, String>> remappingFunction) {
        return super.compute(key, remappingFunction);
    }

    @Override
    public Map<String, String> merge(String key, Map<String, String> value, BiFunction<? super Map<String, String>, ? super Map<String, String>, ? extends Map<String, String>> remappingFunction) {
        return super.merge(key, value, remappingFunction);
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super Map<String, String>> action) {
        super.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super Map<String, String>, ? extends Map<String, String>> function) {
        super.replaceAll(function);
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}

