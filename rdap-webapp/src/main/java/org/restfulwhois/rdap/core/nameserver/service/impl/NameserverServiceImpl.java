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
package org.restfulwhois.rdap.core.nameserver.service.impl;

import org.restfulwhois.rdap.core.common.support.QueryParam;
import org.restfulwhois.rdap.core.domain.service.DomainQueryService;
import org.restfulwhois.rdap.core.nameserver.dao.impl.NameserverQueryDaoImpl;
import org.restfulwhois.rdap.core.nameserver.model.Nameserver;
import org.restfulwhois.rdap.core.nameserver.service.NameserverQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * query service implementation.
 * 
 * RdapController's main query service for querying or searching.
 * 
 * Provide the all tlds to be supported
 * 
 * Requirement from http://www.ietf.org/id/draft-ietf-weirds-rdap-query-10.txt.
 * 
 * @author jiashuo
 * 
 */
@Service
public class NameserverServiceImpl implements NameserverQueryService {

    /**
     * logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(NameserverServiceImpl.class);

    /**
     * nameserver DAO.
     */
    @Autowired
    private NameserverQueryDaoImpl nameserverQueryDao;
    /**
     * nameserver DAO.
     */
    @Autowired
    private DomainQueryService domainService;

    /**
     * query name server by NS queryParam.
     * 
     * @param queryParam
     *            queryParam.
     * @return name server.
     */
    @Override
    public Nameserver queryNameserver(QueryParam queryParam) {
        return nameserverQueryDao.query(queryParam);
    }

    @Override
    public boolean tldInThisRegistry(QueryParam queryParam) {
        return domainService.tldInThisRegistry(queryParam);
    }
}
