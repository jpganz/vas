package com.sigetel.web.web.rest.util;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Utility class for handling pagination.
 *
 * <p>
 * Pagination uses the same principles as the <a href="https://developer.github.com/v3/#pagination">Github API</a>,
 * and follow <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link header)</a>.
 */
public final class JasperHelper {

    private String basicReport;

    private JasperHelper() {
    }

    public JasperHelper(String basicReport) {
        this.basicReport = basicReport;
    }

    public String getBasicReport() {
        return basicReport;
    }

    public void setBasicReport(String basicReport) {
        this.basicReport = basicReport;
    }
}
