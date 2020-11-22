package com.kalo.easpay.utils.generator.mybatis;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinFunction;
import org.mybatis.generator.api.dom.kotlin.KotlinProperty;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 20日 星期五 14:36:42
 */
public class CustomGenerator implements CommentGenerator {

    /**
     * 父类时间
     */
    private boolean suppressDate;
    /**
     * 父类所有注释
     */
    private boolean suppressAllComments;
    /**
     * properties配置文件
     */
    private Properties properties;
    /**
     * properties配置文件
     */
    private Properties systemPro;
    /**
     * 当前时间
     */
    private String currentDateStr;

    public CustomGenerator() {
        this.suppressDate = false;
        this.suppressAllComments = false;
        this.properties = new Properties();
        this.systemPro = System.getProperties();
        this.currentDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * @param properties
     * @return void
     * @Title addConfigurationProperties
     * @Author Panguaxe
     * @Time 2020/11/20 14:39
     * @Description TODO  添加配置属性
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        this.suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        this.suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    private boolean isTrue(String property) {
        return false;
    }

    /**
     * @param s
     * @return java.lang.String
     * @Title toLowerCaseFirstOne
     * @Author Panguaxe
     * @Time 2020/11/20 14:48
     * @Description TODO  首字母转小写
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * @return java.lang.String
     * @Title getDateString
     * @Author Panguaxe
     * @Time 2020/11/20 14:53
     * @Description TODO  此方法返回格式化的日期字符串以包含在Javadoc标记中和XML注释。 如果您不想要日期，则可以返回null在这些文档元素中。
     */
    protected String getDateString() {
        String result = null;
        if (!this.suppressDate) {
            result = this.currentDateStr;
        }
        return result;
    }

    /**
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     * @return void
     * @Title addFieldComment
     * @Author Panguaxe
     * @Time 2020/11/20 14:41
     * @Description TODO  字段添加注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!suppressAllComments) {
            field.addJavaDocLine("/**");
            StringBuilder sb = new StringBuilder();
            sb.append(" * ");
            sb.append(introspectedColumn.getRemarks());
            sb.append("\n");
            sb.append("	* 列名:" + introspectedColumn.getActualColumnName() + ";类型:" + introspectedColumn.getJdbcTypeName()
                    + "(" + introspectedColumn.getLength() + ")" + ";允许空:" + introspectedColumn.isNullable() + ";缺省值:"
                    + introspectedColumn.getDefaultValue());
            field.addJavaDocLine(sb.toString());
            field.addJavaDocLine("*/");
            for (IntrospectedColumn col : introspectedTable.getPrimaryKeyColumns()) {
                if (col.getActualColumnName().equals(introspectedColumn.getActualColumnName())) {
                    field.addAnnotation("@Id");
                    field.addAnnotation("@KeySql(genId = SnowFlakeGenId.class)");
                }
            }
            field.addAnnotation("@Column(name = \"" + introspectedColumn.getActualColumnName() + "\")");
        }
    }

    /**
     * @param field
     * @param introspectedTable
     * @return void
     * @Title addFieldComment
     * @Author Panguaxe
     * @Time 2020/11/20 14:46
     * @Description TODO  属性添加注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (!suppressAllComments) {
            field.addJavaDocLine("/**");
            StringBuilder sb = new StringBuilder();
            sb.append(" * ");
            sb.append(introspectedTable.getFullyQualifiedTable());
            field.addJavaDocLine(sb.toString());
            field.addJavaDocLine(" */");
        }
    }

    /**
     * @param topLevelClass
     * @param introspectedTable
     * @return void
     * @Title addModelClassComment
     * @Author Panguaxe
     * @Time 2020/11/20 14:47
     * @Description TODO  类添加注释
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (!suppressAllComments) {
            topLevelClass.addImportedType("lombok.Data");
            topLevelClass.addImportedType("lombok.NoArgsConstructor");
            topLevelClass.addImportedType("lombok.AllArgsConstructor");
            topLevelClass.addImportedType("javax.persistence.Table");
            topLevelClass.addImportedType("javax.persistence.Column");
            topLevelClass.addImportedType("javax.persistence.Id");
            topLevelClass.addImportedType("org.apache.ibatis.type.Alias");
            topLevelClass.addImportedType("tk.mybatis.mapper.annotation.KeySql");
            topLevelClass.addImportedType("com.kalo.easpay.utils.generator.SnowFlakeGenId");
            topLevelClass.addJavaDocLine("/**");
            StringBuilder sb = new StringBuilder();
            sb.append(" * ");
            sb.append(introspectedTable.getFullyQualifiedTable());
            sb.append(introspectedTable.getRemarks());
            topLevelClass.addJavaDocLine(sb.toString());
            sb.setLength(0);
            sb.append(" * @Author ");
            sb.append(systemPro.getProperty("user.name"));
            sb.append(" ");
            sb.append(currentDateStr);
            topLevelClass.addJavaDocLine(" */");
            topLevelClass.addAnnotation("@Data");
            topLevelClass.addAnnotation("@NoArgsConstructor");
            topLevelClass.addAnnotation("@AllArgsConstructor");
            topLevelClass.addAnnotation("@Table(name = \"" + introspectedTable.getFullyQualifiedTable() + "\")");
            topLevelClass.addAnnotation("@Alias(\"" + introspectedTable.getRemarks() +  "\")");
        }

    }

    /**
     * @param innerClass
     * @param introspectedTable
     * @return void
     * @Title addClassComment
     * @Author Panguaxe
     * @Time 2020/11/20 14:54
     * @Description TODO  类添加注释
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (!this.suppressAllComments) {
            StringBuilder sb = new StringBuilder();
            innerClass.addJavaDocLine("/**");
            sb.append(" * ");
            sb.append(introspectedTable.getFullyQualifiedTable());
            sb.append(" ");
            sb.append(getDateString());
            innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
            innerClass.addJavaDocLine(" */");
        }
    }

    /**
     * @param innerClass
     * @param introspectedTable
     * @param b
     * @return void
     * @Title addClassComment
     * @Author Panguaxe
     * @Time 2020/11/20 14:54
     * @Description TODO  类添加注释
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {
        if (!suppressAllComments) {
            StringBuilder sb = new StringBuilder();
            innerClass.addJavaDocLine("/**");
            sb.append(" * ");
            sb.append(introspectedTable.getFullyQualifiedTable());
            innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
            sb.setLength(0);
            sb.append(" * @Author ");
            sb.append(systemPro.getProperty("user.name"));
            sb.append(" ");
            sb.append(currentDateStr);
            innerClass.addJavaDocLine(" */");
        }
    }

    /**
     * @param innerEnum
     * @param introspectedTable
     * @return void
     * @Title addEnumComment
     * @Author Panguaxe
     * @Time 2020/11/20 14:57
     * @Description TODO  枚举添加注释
     */
    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (!suppressAllComments) {
            innerEnum.addJavaDocLine("/**");
            StringBuilder sb = new StringBuilder();
            sb.append(" * ");
            sb.append(introspectedTable.getFullyQualifiedTable());
            innerEnum.addJavaDocLine(sb.toString().replace("\n", " "));
            innerEnum.addJavaDocLine(" */");
        }
    }

    /**
     * @param method
     * @param introspectedTable
     * @param introspectedColumn
     * @return void
     * @Title addGetterComment
     * @Author Panguaxe
     * @Time 2020/11/20 14:57
     * @Description TODO  Getter添加注释
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!suppressAllComments) {
            method.addJavaDocLine("/**");
            StringBuilder sb = new StringBuilder();
            method.addJavaDocLine(sb.toString().replace("\n", " "));
            sb.setLength(0);
            sb.append(" * @return ");
            sb.append(introspectedColumn.getActualColumnName());
            sb.append(" ");
            sb.append(introspectedColumn.getRemarks());
            method.addJavaDocLine(sb.toString().replace("\n", " "));
            method.addJavaDocLine(" */");
        }
    }

    /**
     * @param method
     * @param introspectedTable
     * @param introspectedColumn
     * @return void
     * @Title addSetterComment
     * @Author Panguaxe
     * @Time 2020/11/20 14:59
     * @Description TODO  Setter添加注释
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!suppressAllComments) {
            method.addJavaDocLine("/**");
            StringBuilder sb = new StringBuilder();
            sb.append(" * ");
            sb.append(introspectedColumn.getRemarks());
            method.addJavaDocLine(sb.toString().replace("\n", " "));
            Parameter parm = method.getParameters().get(0);
            sb.setLength(0);
            sb.append(" * @param ");
            sb.append(parm.getName());
            sb.append(" ");
            sb.append(introspectedColumn.getRemarks());
            method.addJavaDocLine(sb.toString().replace("\n", " "));
            method.addJavaDocLine(" */");
        }
    }

    /**
     * @param method
     * @param introspectedTable
     * @return void
     * @Title addGeneralMethodComment
     * @Author Panguaxe
     * @Time 2020/11/20 15:01
     * @Description TODO  普通方法添加注释
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (!suppressAllComments) {
            StringBuilder sb = new StringBuilder();
            sb.append("/**");
            sb.append("\n");
            sb.append("	* ");
            sb.append("\n");
            sb.append("	* @Author：Panguaxe" + "\n");
            if (!suppressDate) {
                sb.append("	* @date " + currentDateStr + "\n");
            }
            List<Parameter> parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                sb.append("	* @param " + parameter.getName() + "\n");
            }
            sb.append("	* @return " + method.getReturnType());
            sb.append("\n" + " */");
            method.addJavaDocLine(sb.toString());
        }
    }
    /**
     * @Title addJavaFileComment
     * @Author Panguaxe
     * @param compilationUnit
     * @return void
     * @Time   2020/11/20 15:04
     * @Description      TODO  文件版权注释
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        compilationUnit.addFileCommentLine("/*");
        compilationUnit.addFileCommentLine("*");
        compilationUnit.addFileCommentLine("* " + compilationUnit.getType().getShortName() + ".java");
        compilationUnit.addFileCommentLine("* Copyright(C) 2019-10-29 Kalo Tech");
        compilationUnit.addFileCommentLine("* @date " + sdf.format(new Date()) + "");
        compilationUnit.addFileCommentLine("*/");
    }

    @Override
    public void addComment(XmlElement xmlElement) {

    }

    @Override
    public void addRootComment(XmlElement xmlElement) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFileComment(KotlinFile kotlinFile) {

    }

    @Override
    public void addGeneralFunctionComment(KotlinFunction kf, IntrospectedTable introspectedTable, Set<String> imports) {

    }

    @Override
    public void addGeneralPropertyComment(KotlinProperty property, IntrospectedTable introspectedTable, Set<String> imports) {

    }
}
