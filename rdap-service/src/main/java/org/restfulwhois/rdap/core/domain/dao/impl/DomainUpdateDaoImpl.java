/*
 * Copyright (c) 2012 - 2015, Internet Corporation for Assigned Names and
 * Numbers (ICANN) and China Internet Network Information Center (CNNIC)
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * * Neither the name of the ICANN, CNNIC nor the names of its contributors may
 *  be used to endorse or promote products derived from this software without
 *  specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL ICANN OR CNNIC BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package org.restfulwhois.rdap.core.domain.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.restfulwhois.rdap.common.dao.AbstractUpdateDao;
import org.restfulwhois.rdap.common.model.Domain;
import org.restfulwhois.rdap.common.model.base.BaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * @author jiashuo
 * 
 */
@Repository
public class DomainUpdateDaoImpl extends AbstractUpdateDao<Domain> {
    private static final String SQL_CREATE_DOMAIN =
            "INSERT INTO RDAP_DOMAIN"
                    + " (HANDLE,LDH_NAME,UNICODE_NAME,PORT43,LANG,TYPE,NETWORK_ID,CUSTOM_PROPERTIES)"
                    + " values(?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_DOMAIN =
            "UPDATE RDAP_DOMAIN"
                    + " SET HANDLE=?,LDH_NAME=?,UNICODE_NAME=?,PORT43=?,LANG=?,NETWORK_ID=?"
                    + " ,CUSTOM_PROPERTIES=?";
    private static final String SQL_DELETE_DOMAIN =
            "DELETE FROM RDAP_DOMAIN where HANDLE=?";
    /**
     * logger.
     */
    protected static final Logger LOGGER = LoggerFactory
            .getLogger(DomainUpdateDaoImpl.class);

    @Override
    public Domain create(final Domain model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(SQL_CREATE_DOMAIN, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, model.getHandle());
                ps.setString(2, model.getLdhName());
                ps.setString(3, model.getUnicodeName());
                ps.setString(4, model.getPort43());
                ps.setString(5, model.getLang());
                ps.setString(6, model.getType().getName());
                ps.setObject(7, model.getNetworkId());
                ps.setString(8, model.getCustomPropertiesJsonVal());
            }
        });
        model.setId(keyHolder.getKey().longValue());
        return model;
    }

    @Override
    public void update(final Domain model) {
        jdbcTemplate.update(SQL_UPDATE_DOMAIN, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, model.getHandle());
                ps.setString(2, model.getLdhName());
                ps.setString(3, model.getUnicodeName());
                ps.setString(4, model.getPort43());
                ps.setString(5, model.getLang());
                ps.setObject(6, model.getNetworkId());
                ps.setString(7, model.getCustomPropertiesJsonVal());
            }
        });
    }

    @Override
    public void delete(Domain model) {
        // TODO Auto-generated method stub
//        SQL_DELETE_DOMAIN
    }

    @Override
    public Long findIdByHandle(String handle) {
        return super.findIdByHandle(handle, "DOMAIN_ID", "RDAP_DOMAIN");
    }

}
