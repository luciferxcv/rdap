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
package org.restfulwhois.rdap.filters.queryFilter;

import org.restfulwhois.rdap.core.common.filter.QueryFilterResult;
import org.restfulwhois.rdap.core.common.support.QueryParam;
import org.restfulwhois.rdap.core.common.util.RestResponseUtil;
import org.restfulwhois.rdap.core.common.util.StringUtil;
import org.restfulwhois.rdap.core.domain.queryparam.DomainSearchParam;
import org.restfulwhois.rdap.core.domain.service.DomainQueryService;
import org.restfulwhois.rdap.core.nameserver.queryparam.NameserverSearchParam;
import org.restfulwhois.rdap.redirect.bean.RedirectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DomainAndNsRedirectQueryFilter.
 * 
 * @author jiashuo
 * 
 */
@Component
public class DomainAndNsRedirectQueryFilter extends AbstractRedirectQueryFilter {

    /**
     * logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DomainAndNsRedirectQueryFilter.class);

    /**
     * query service.
     */
    @Autowired
    protected DomainQueryService queryService;

    @Override
    protected QueryFilterResult queryRedirect(QueryParam queryParam) {
        LOGGER.debug("   query redirect for domain/ns :{}", queryParam);
        if (queryParam instanceof DomainSearchParam
                || queryParam instanceof NameserverSearchParam) {
            return null;
        }
        if (queryService.tldInThisRegistry(queryParam)) {
            LOGGER.debug("   tld is in this registry, so not query redirect.");
            return null;
        }
        RedirectResponse redirect = redirectService.queryDomain(queryParam);
        if (!redirectService.isValidRedirect(redirect)) {
            return null;
        }
        String redirectUrl =
                StringUtil.generateEncodedRedirectURL(
                        queryParam.getOriginalQ(), queryParam.getQueryUri()
                                .getName(), redirect.getUrl());
        return new QueryFilterResult(
                RestResponseUtil.createResponse301(redirectUrl));
    }

}
