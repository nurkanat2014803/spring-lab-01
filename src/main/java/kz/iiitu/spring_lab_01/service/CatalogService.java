package kz.iiitu.spring_lab_01.service;

import kz.iiitu.spring_lab_01.audit.Audited;
import org.springframework.beans.factory.annotation.Autowired; // ← НОВЫЙ импорт
import org.springframework.stereotype.Service;
import kz.iiitu.spring_lab_01.audit.RequiresRole;

import java.util.List;
import java.util.stream.IntStream;


@Service
public class CatalogService {


    @Autowired
    private CatalogService self;   // ← НОВОЕ поле

    public String removeTwice(long id) {
        String first = self.remove(id);       // ← было this.remove(id)
        String second = self.remove(id + 1);  // ← было this.remove(id + 1)
        return first + " " + second;
    }
    public String findById(long id) {
        sleep(50);
        return "Item no. " + id;
    }
    @Audited(action = "CATALOG_LIST", logArguments = true)   // ← НОВАЯ строка
    public List<String> findAll(int limit) {
        sleep(300);
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "Item no. " + i)
                .toList();
    }
    @Audited(action = "CATALOG_REMOVE")
    @RequiresRole("ADMIN")   // ← новое
    public String remove(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid identifier: " + id);
        }
        return "Removed item no. " + id;
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}