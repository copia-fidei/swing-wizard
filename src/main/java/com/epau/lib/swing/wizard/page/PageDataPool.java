package com.epau.lib.swing.wizard.page;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.Consumer;

public class PageDataPool {

    private final List<PageData> pageDataList = new ArrayList<>();

    public PageDataPool() {}

    public void add(PageData pageData) {
        pageDataList.add(pageData);
    }

    public <T extends PageData> Optional<T> get(Class<T> pageDataClass) {
        return pageDataList.stream().filter(pageDataClass::isInstance).map(pageDataClass::cast).findFirst();
    }

    public void forEach(Consumer<PageData> action) {
        for (var pageData : pageDataList) {
            action.accept(pageData);
        }
    }

    public ListIterator<PageData> listIterator() {
        return pageDataList.listIterator();
    }

    public int size() {
        return pageDataList.size();
    }
}
