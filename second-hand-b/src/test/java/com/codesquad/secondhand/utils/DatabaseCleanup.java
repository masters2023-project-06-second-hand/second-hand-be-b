package com.codesquad.secondhand.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DatabaseCleanup {

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tabelNames;

    // 테스트 초기화 시 데이터를 지우면 안되는 테이블명
    private final List<String> excludeTables = Arrays.asList("region", "category");

    @PostConstruct
    public void init() {
        tabelNames = entityManager.getMetamodel().getEntities().stream()
                .filter(entity -> entity.getJavaType().getAnnotation(Entity.class) != null)
                .map(entity -> entity.getJavaType().getAnnotation(Table.class).name())
                .filter(tableName -> !excludeTables.contains(tableName))
                .collect(Collectors.toList());
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        for (Object tableName : tabelNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT = 1")
                    .executeUpdate();
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }
}
