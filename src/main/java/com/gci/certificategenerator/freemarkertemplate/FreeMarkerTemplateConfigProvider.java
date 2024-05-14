package com.gci.certificategenerator.freemarkertemplate;

import com.gci.certificategenerator.errors.custom.ApplicationException;
import com.gci.certificategenerator.logger.Loggers;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;

import static com.gci.certificategenerator.constants.CertificateGeneratorConstants.FTL_SUFFIX;

@Component
public class FreeMarkerTemplateConfigProvider {
    private static final Logger log = Loggers.APPLICATION_LOG;
    @Value("${templates.path}")
    private String templateResourcesPath;
    private final ResourceLoader resourceLoader;
    private final FreeMarkerTemplateRegistry templateRegistry;
    @Value("${template.basePath}")
    private String templateBasePath;

    public FreeMarkerTemplateConfigProvider(
            ResourceLoader resourceLoader,
            FreeMarkerTemplateRegistry templateRegistry
    ) {
        this.resourceLoader = resourceLoader;
        this.templateRegistry = templateRegistry;
    }

    @PostConstruct
    public void loadTemplateConfig() {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
            configuration.setClassForTemplateLoading(FreeMarkerTemplateConfigProvider.class,templateBasePath);
            ResourcePatternResolver resolver = (ResourcePatternResolver) resourceLoader;
            Resource[] resources = resolver.getResources(templateResourcesPath);
            if (resources.length == 0) {
                log.error("No templates in the directory");
                return;
            }

            final Map<String, Template> templateMap = templateRegistry.getTemplateMap();
            for (Resource resource: resources) {
                String templateName = resource.getFilename();
                Template template = configuration.getTemplate(templateName);
                templateMap.put(StringUtils.remove(templateName, FTL_SUFFIX), template);
            }
        } catch (IOException e) {
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
