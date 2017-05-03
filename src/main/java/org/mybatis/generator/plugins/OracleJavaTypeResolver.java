package org.mybatis.generator.plugins;

import java.math.BigDecimal;
import java.sql.Types;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

public class OracleJavaTypeResolver extends JavaTypeResolverDefaultImpl {

	public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
		FullyQualifiedJavaType answer;
		JdbcTypeInformation jdbcTypeInformation = typeMap.get(introspectedColumn.getJdbcType());

		if (jdbcTypeInformation == null) {
			switch (introspectedColumn.getJdbcType()) {
			case Types.DECIMAL:
			case Types.NUMERIC:
				if (forceBigDecimals) {
					answer = new FullyQualifiedJavaType(BigDecimal.class.getName());
				} else {
					if (introspectedColumn.getScale() > 0) {// 如果包含小数点则转换成Double
						answer = new FullyQualifiedJavaType(Double.class.getName());
					} else {
						if (introspectedColumn.getLength() > 18) {
							answer = new FullyQualifiedJavaType(BigDecimal.class.getName());
						} else if (introspectedColumn.getLength() > 9) {
							answer = new FullyQualifiedJavaType(Long.class.getName());
						} else if (introspectedColumn.getLength() > 4) {
							answer = new FullyQualifiedJavaType(Integer.class.getName());
						} else {
							answer = new FullyQualifiedJavaType(Short.class.getName());
						}
					}
				}
				break;

			default:
				answer = null;
				break;
			}
		} else {
			answer = jdbcTypeInformation.getFullyQualifiedJavaType();
		}

		return answer;
	}

	public String calculateJdbcTypeName(IntrospectedColumn introspectedColumn) {
		String answer;
		JdbcTypeInformation jdbcTypeInformation = typeMap.get(introspectedColumn.getJdbcType());

		if (jdbcTypeInformation == null) {
			switch (introspectedColumn.getJdbcType()) {
			case Types.DECIMAL:
				if (forceBigDecimals) {
					answer = "DECIMAL"; //$NON-NLS-1$
				} else {
					if (introspectedColumn.getScale() > 0) {// 如果包含小数点则转换成Double
						answer = "DOUBLE";
					} else {
						if (introspectedColumn.getLength() > 18) {
							answer = "DECIMAL";
						} else if (introspectedColumn.getLength() > 9) {
							answer = "LONG";
						} else if (introspectedColumn.getLength() > 4) {
							answer = "INTEGER";
						} else {
							answer = "SHORT";
						}
					}
				}
				break;
			case Types.NUMERIC:
				answer = "NUMERIC"; //$NON-NLS-1$
				break;
			default:
				answer = null;
				break;
			}
		} else {
			answer = jdbcTypeInformation.getJdbcTypeName();
		}

		return answer;
	}
}
