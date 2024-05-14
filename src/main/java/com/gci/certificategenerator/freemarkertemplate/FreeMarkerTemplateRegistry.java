package com.gci.certificategenerator.freemarkertemplate;

import com.gci.certificategenerator.logger.Loggers;
import freemarker.template.Template;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@ToString
public class FreeMarkerTemplateRegistry {
    private static final Logger log = Loggers.APPLICATION_LOG;
    private final Map<String, Template> templateMap = new HashMap<>();
}
