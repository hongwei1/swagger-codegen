package io.swagger.codegen.languages;

import io.swagger.codegen.*;

import java.util.*;

public class ObpServerCodegen extends AbstractScalaCodegen implements CodegenConfig {

    protected String groupId = "io.swagger";
    protected String artifactId = "swagger-server";
    protected String artifactVersion = "1.0.0";

    public ObpServerCodegen() {
        super();
        outputFolder = "generated-code/obp";
        modelTemplateFiles.put("model.mustache", ".scala");
        apiTemplateFiles.put("api.mustache", ".scala");
        embeddedTemplateDir = templateDir = "obp";
        apiPackage = "code.api.builder";
        modelPackage = "code.api.builder.model";

        setReservedWordsLowerCase(
                Arrays.asList(
                        "abstract", "continue", "for", "new", "switch", "assert",
                        "default", "if", "package", "synchronized", "boolean", "do", "goto", "private",
                        "this", "break", "double", "implements", "protected", "throw", "byte", "else",
                        "import", "public", "throws", "case", "enum", "instanceof", "return", "transient",
                        "catch", "extends", "int", "short", "try", "char", "final", "interface", "static",
                        "void", "class", "finally", "long", "strictfp", "volatile", "const", "float",
                        "native", "super", "while", "type")
        );

        defaultIncludes = new HashSet<String>(
                Arrays.asList("double",
                        "Int",
                        "Long",
                        "Float",
                        "Double",
                        "char",
                        "float",
                        "String",
                        "boolean",
                        "Boolean",
                        "Double",
                        "Integer",
                        "Long",
                        "Float",
                        "List",
                        "Set",
                        "Map")
        );

        additionalProperties.put("appName", "Swagger Sample");
        additionalProperties.put("appDescription", "A sample swagger server");
        additionalProperties.put("infoUrl", "http://swagger.io");
        additionalProperties.put("infoEmail", "apiteam@swagger.io");
        additionalProperties.put("licenseInfo", "All rights reserved");
        additionalProperties.put("licenseUrl", "http://apache.org/licenses/LICENSE-2.0.html");
        additionalProperties.put(CodegenConstants.INVOKER_PACKAGE, invokerPackage);
        additionalProperties.put(CodegenConstants.GROUP_ID, groupId);
        additionalProperties.put(CodegenConstants.ARTIFACT_ID, artifactId);
        additionalProperties.put(CodegenConstants.ARTIFACT_VERSION, artifactVersion);
        
        supportingFiles.add(new SupportingFile("pom.xml", "", "pom.xml"));
        supportingFiles.add(new SupportingFile("README.md", "", "README.md"));
        supportingFiles.add(new SupportingFile("roadmap.md", "", "roadmap.md"));
        supportingFiles.add(new SupportingFile("Akka.README.md", "", "Akka.README.md"));
        supportingFiles.add(new SupportingFile("./src/main/resources/logback.xml", "/src/main/resources", "logback.xml"));
        supportingFiles.add(new SupportingFile("./src/main/resources/props/default.props", "/src/main/resources/props/", "default.props"));
//        supportingFiles.add(new SupportingFile("./src/main/scala/bootstrap/liftweb/Boot.scala", "src/main/scala/bootstrap/liftweb/", "Boot.scala"));
        supportingFiles.add(new SupportingFile(".gitignore", "", ".gitignore"));
        supportingFiles.add(new SupportingFile("cheat_sheet.md", "", "cheat_sheet.md"));
        supportingFiles.add(new SupportingFile("CONTRIBUTING.md", "", "CONTRIBUTING.md"));
        supportingFiles.add(new SupportingFile("FAQ.md", "", "FAQ.md"));
        supportingFiles.add(new SupportingFile("formParam.mustache", "", "formParam.mustache"));
        supportingFiles.add(new SupportingFile("formParamMustache.mustache", "", "formParamMustache.mustache"));
        supportingFiles.add(new SupportingFile("GNU_AFFERO_GPL_V3_19_Nov_1997.txt", "", "GNU_AFFERO_GPL_V3_19_Nov_1997.txt"));
        supportingFiles.add(new SupportingFile("Harmony_Individual_Contributor_Assignment_Agreement.txt", "", "Harmony_Individual_Contributor_Assignment_Agreement.txt"));
        supportingFiles.add(new SupportingFile("Harmony_Individual_Contributor_Assignment_Agreement.txt", "", "Harmony_Individual_Contributor_Assignment_Agreement.txt"));

        //TODO Will remove

        instantiationTypes.put("array", "ArrayList");
        instantiationTypes.put("map", "HashMap");

        importMapping = new HashMap<String, String>();
        importMapping.put("BigDecimal", "java.math.BigDecimal");
        importMapping.put("UUID", "java.util.UUID");
        importMapping.put("File", "java.io.File");
        importMapping.put("Date", "java.util.Date");
        importMapping.put("Timestamp", "java.sql.Timestamp");
        importMapping.put("Map", "java.util.Map");
        importMapping.put("HashMap", "java.util.HashMap");
        importMapping.put("Array", "java.util.List");
        importMapping.put("ArrayList", "java.util.ArrayList");
        importMapping.put("DateTime", "org.joda.time.DateTime");
        importMapping.put("LocalDateTime", "org.joda.time.LocalDateTime");
        importMapping.put("LocalDate", "org.joda.time.LocalDate");
        importMapping.put("LocalTime", "org.joda.time.LocalTime");

        typeMapping.put("array", "List");
        typeMapping.put("map", "Map");
        typeMapping.put("List", "List");
        typeMapping.put("boolean", "MappedBoolean(this)");
        typeMapping.put("string", "MappedString(this, 255)");
        typeMapping.put("int", "MappedInt(this)");
        typeMapping.put("float", "MappedDouble(this)");
        typeMapping.put("number", "MappedDouble(this)");
        typeMapping.put("DateTime", "MappedDateTime(this)");
        typeMapping.put("long", "MappedLong(this)");
        typeMapping.put("short", "MappedShort(this)");
        typeMapping.put("char", "MappedString(this, 255)");
        typeMapping.put("double", "MappedDouble(this)");
        typeMapping.put("object", "Object");
        typeMapping.put("integer", "MappedInt(this)");
        typeMapping.put("ByteArray", "byte[]");
        typeMapping.put("binary", "byte[]");
        typeMapping.put("file", "File");
        typeMapping.put("UUID", "UUIDString(this)");

        //TODO binary should be mapped to byte array
        // mapped to String as a workaround
        typeMapping.put("binary", "MappedString(this, 255)");
    }

    @Override
    public CodegenType getTag() {
        return CodegenType.SERVER;
    }

    @Override
    public String getName() {
        return "obp";
    }

    @Override
    public String getHelp() {
        return "Generates a Obp server application.";
    }

    @Override
    public Map<String, Object> postProcessOperations(Map<String, Object> objs) {
        Map<String, Object> operations = (Map<String, Object>) objs.get("operations");
        List<CodegenOperation> operationList = (List<CodegenOperation>) operations.get("operation");
        for (CodegenOperation op : operationList) {
            // force http method to lower case
            op.httpMethod = op.httpMethod.toLowerCase();
 
            String[] items = op.path.split("/", -1);
            String scalaPath = "";
            int pathParamIndex = 0;

            for (int i = 0; i < items.length; ++i) {
                if (items[i].matches("^\\{(.*)\\}$")) { // wrap in {}
                    scalaPath = scalaPath + ":" + items[i].replace("{", "").replace("}", "");
                    pathParamIndex++;
                } else {
                    scalaPath = scalaPath + items[i];
                }

                if (i != items.length -1) {
                    scalaPath = scalaPath + "/";
                }
            }

            op.vendorExtensions.put("x-scalatra-path", scalaPath);
        }

        return objs;
    }

}
