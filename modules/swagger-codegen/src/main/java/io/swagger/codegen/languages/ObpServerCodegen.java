package io.swagger.codegen.languages;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import io.swagger.codegen.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Writer;
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
        apiPackage = "code.api.berlin.group.v1_3";
        modelPackage = "code.api.berlin.group.v1_3.model";

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
        
//        supportingFiles.add(new SupportingFile("pom.xml", "", "pom.xml"));
//        supportingFiles.add(new SupportingFile("README.md", "", "README.md"));
//        supportingFiles.add(new SupportingFile("roadmap.md", "", "roadmap.md"));
//        supportingFiles.add(new SupportingFile("Akka.README.md", "", "Akka.README.md"));
//        supportingFiles.add(new SupportingFile("./src/main/resources/logback.xml", "/src/main/resources", "logback.xml"));
//        supportingFiles.add(new SupportingFile("./src/main/resources/props/default.props", "/src/main/resources/props/", "default.props"));
//        supportingFiles.add(new SupportingFile("./src/main/scala/bootstrap/liftweb/Boot.scala", "src/main/scala/bootstrap/liftweb/", "Boot.scala"));
//        supportingFiles.add(new SupportingFile(".gitignore", "", ".gitignore"));
//        supportingFiles.add(new SupportingFile("cheat_sheet.md", "", "cheat_sheet.md"));
//        supportingFiles.add(new SupportingFile("CONTRIBUTING.md", "", "CONTRIBUTING.md"));
//        supportingFiles.add(new SupportingFile("FAQ.md", "", "FAQ.md"));
//        supportingFiles.add(new SupportingFile("formParam.mustache", "", "formParam.mustache"));
//        supportingFiles.add(new SupportingFile("formParamMustache.mustache", "", "formParamMustache.mustache"));
//        supportingFiles.add(new SupportingFile("GNU_AFFERO_GPL_V3_19_Nov_1997.txt", "", "GNU_AFFERO_GPL_V3_19_Nov_1997.txt"));
//        supportingFiles.add(new SupportingFile("Harmony_Individual_Contributor_Assignment_Agreement.txt", "", "Harmony_Individual_Contributor_Assignment_Agreement.txt"));
//        supportingFiles.add(new SupportingFile("Harmony_Individual_Contributor_Assignment_Agreement.txt", "", "Harmony_Individual_Contributor_Assignment_Agreement.txt"));

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

            String[] items = op.path.split("/", -1);
            String scalaPath = "";
            String endpointPath = "";
            int pathParamIndex = 0;

            for (int i = 0; i < items.length; ++i) {
                if (items[i].matches("^\\{(.*)\\}$")) { // wrap in {}
                    String param = items[i].replace("{", "").replace("}", "").replace('-', '_');
                    scalaPath = scalaPath + param.toUpperCase();
                    endpointPath = endpointPath + " :: " + param.toLowerCase();
                    pathParamIndex++;
                } else {
                    scalaPath = scalaPath + items[i];
                    if(StringUtils.isNotBlank(items[i])){
                        String infix = StringUtils.isBlank(endpointPath) ? "\"" : ":: \"";
                        endpointPath = endpointPath + infix + items[i] + "\"";
                    }
                }

                if (i != items.length -1) {
                    scalaPath = scalaPath + "/";
                }
            }
            endpointPath += " :: Nil";

            String responseBody ="";
             if (op.examples==null) {
                 responseBody = "" ;
             } else {
                 responseBody = op.examples.stream().filter(it -> "application/json".equals(it.get("contentType"))).map(it -> it.get("example")).findFirst().orElse("");
             }

            String requestBody = "";
            if(op.bodyParam != null && op.bodyParam.isBodyParam) {

               requestBody =  op.requestBodyExamples.stream().filter(it -> "application/json".equals(it.get("contentType"))).map(it-> it.get("example")).findFirst().orElse("");
            }

            op.vendorExtensions.put("obp-responseBody", responseBody);
            op.vendorExtensions.put("obp-requestBody", requestBody);
            op.vendorExtensions.put("x-obp-path", scalaPath);
            op.vendorExtensions.put("endpointPath", endpointPath);
            op.vendorExtensions.put("jsonMethod", "Json"+ StringUtils.capitalize(op.httpMethod.toLowerCase()));
        }

        return objs;
    }

    @Override
    public void processOpts() {
        super.processOpts();
        //TODO ADD lambda
        additionalProperties.put("isNotId", new Mustache.Lambda() {
            @Override
            public void execute(Template.Fragment fragment, Writer writer) throws IOException {
                writer.write(fragment.execute().replaceAll("\\r|\\n", ""));
            }
        });
    }

    @Override
    public void postProcessModelProperty(CodegenModel model, CodegenProperty property) {
        super.postProcessModelProperty(model, property);

        model.classname = model.classname.replaceAll("\\.","_").replaceAll("\\-","_");
        
        if ("id".equals(property.name) || "createdAt".equals("name")) {
            property.isInherited = true;
        }
        
//        model.name = model.name.replaceAll(".","_").replaceAll("-","_");
//        model.classVarName = model.classVarName.replaceAll(".","_").replaceAll("-","_");
//        model.classFilename = model.classVarName.replaceAll(".","_").replaceAll("-","_");
    }
    
//    // override with any special post-processing for all models
//    @SuppressWarnings({ "static-method", "unchecked" })
//    @Override
//    public Map<String, Object> postProcessAllModels(Map<String, Object> objs) {
//        if (supportsInheritance) {
//            // Index all CodegenModels by model name.
//            Map<String, CodegenModel> allModels = new HashMap<String, CodegenModel>();
//            for (Map.Entry<String, Object> entry : objs.entrySet()) {
//                String modelName = toModelName(entry.getKey());
//                Map<String, Object> inner = (Map<String, Object>) entry.getValue();
//                List<Map<String, Object>> models = (List<Map<String, Object>>) inner.get("models");
//                for (Map<String, Object> mo : models) {
//                    CodegenModel cm = (CodegenModel) mo.get("model");
//                    allModels.put(modelName, cm);
//                }
//            }
//            // Fix up all parent and interface CodegenModel references.
//            for (CodegenModel cm : allModels.values()) {
//                if (cm.parent != null) {
//                    cm.parentModel = allModels.get(cm.parent);
//                }
//                if (cm.interfaces != null && !cm.interfaces.isEmpty()) {
//                    cm.interfaceModels = new ArrayList<CodegenModel>(cm.interfaces.size());
//                    for (String intf : cm.interfaces) {
//                        CodegenModel intfModel = allModels.get(intf);
//                        if (intfModel != null) {
//                            cm.interfaceModels.add(intfModel);
//                        }
//                    }
//                }
//            }
//            // Let parent know about all its children
//            for (String name : allModels.keySet()) {
//                CodegenModel cm = allModels.get(name);
//                CodegenModel parent = allModels.get(cm.parent);
//                // if a discriminator exists on the parent, don't add this child to the inheritance hierarchy
//                // TODO Determine what to do if the parent discriminator name == the grandparent discriminator name
//                while (parent != null) {
//                    if (parent.children == null) {
//                        parent.children = new ArrayList<CodegenModel>();
//                    }
//                    parent.children.add(cm);
//                    if (parent.discriminator == null) {
//                        parent = allModels.get(parent.parent);
//                    } else {
//                        parent = null;
//                    }
//                }
//            }
//        } else{
//            // Index all CodegenModels by model name.
//            Map<String, CodegenModel> allModels = new HashMap<String, CodegenModel>();
//            for (Map.Entry<String, Object> entry : objs.entrySet()) {
//                String modelName = toModelName(entry.getKey()).replace("\\.","_");
//            }
//        }
//        return objs;
//    }

}
