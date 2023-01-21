package com.olshevchenko.weatherapi.utils;

import com.olshevchenko.weatherapi.security.model.Role;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Oleksandr Shevchenko
 */
public class PGSQLEnumType extends EnumType<Role> {

    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.OTHER);
        }
        else {
            preparedStatement.setObject(index, value.toString(), Types.OTHER);
        }
    }
}
