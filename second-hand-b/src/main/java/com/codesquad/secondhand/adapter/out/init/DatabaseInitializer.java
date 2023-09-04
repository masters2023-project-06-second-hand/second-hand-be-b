package com.codesquad.secondhand.adapter.out.init;

import com.codesquad.secondhand.application.port.out.CategoryRepository;
import com.codesquad.secondhand.application.port.out.RegionRepository;
import com.codesquad.secondhand.domain.product.Category;
import com.codesquad.secondhand.domain.region.Region;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class DatabaseInitializer implements ApplicationRunner {

    private static final int REGION_NAME_INDEX = 0;
    private static final int CATEGORY_NAME_INDEX = 0;
    private static final int CATEGORY_IMG_URL_INDEX = 1;
    @Value(value = "${data.init.path.region}")
    private String REGION_FILE_PATH;
    @Value(value = "${data.init.path.category}")
    private String CATEGORY_FILE_PATH;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) throws IOException {
        initializeRegion();
        initializeCategory();
    }

    private void initializeRegion() throws IOException {
        ClassPathResource resource = new ClassPathResource(REGION_FILE_PATH);
        InputStream is = resource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        List<Region> regionsToSave = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] row = StringUtils.commaDelimitedListToStringArray(line);
            String name = row[REGION_NAME_INDEX];
            Region region = new Region(name);
            regionsToSave.add(region);
        }
        regionRepository.saveAll(regionsToSave);
        br.close();
    }

    private void initializeCategory() throws IOException {
        ClassPathResource resource = new ClassPathResource(CATEGORY_FILE_PATH);
        InputStream is = resource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        List<Category> categoriesToSave = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] row = StringUtils.commaDelimitedListToStringArray(line);
            String name = row[CATEGORY_NAME_INDEX];
            String imgUrl = row[CATEGORY_IMG_URL_INDEX];
            Category category = new Category(name, imgUrl);
            categoriesToSave.add(category);
        }
        categoryRepository.saveAll(categoriesToSave);
        br.close();
    }
}
