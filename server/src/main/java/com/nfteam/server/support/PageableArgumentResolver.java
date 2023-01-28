package com.nfteam.server.support;

import com.nfteam.server.exception.support.PageParameterInvalidException;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.regex.Pattern;

@Component
public class PageableArgumentResolver extends PageableHandlerMethodArgumentResolver {

    private static final Pattern NUMBER = Pattern.compile("^[0-9]*$");

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return super.supportsParameter(parameter);
    }

    @Override
    public Pageable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String pageArg = webRequest.getParameter("page");
        String sizeArg = webRequest.getParameter("size");
        validatePageAndSizeNumber(pageArg, sizeArg);

        Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
        Sort sort = getSort(webRequest, pageable.getSort());

        return PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), sort);
    }

    private void validatePageAndSizeNumber(String pageArg, String sizeArg) {
        if (pageArg == null || sizeArg == null) return;
        if (!NUMBER.matcher(pageArg).matches() ||
                !NUMBER.matcher(sizeArg).matches()) {
            throw new PageParameterInvalidException();
        }
    }

    private Sort getSort(NativeWebRequest webRequest, Sort sort) {
        String requestUri = ((ServletWebRequest) webRequest).getRequest().getRequestURI();
        if (requestUri.contains("/api/items/collections")) {
            sort = sort.and(Sort.by("itemId").ascending());
        } else if (requestUri.contains("/api/search")) {
            sort = sort.and(Sort.by("createdDate").descending());
        } else {
            sort = sort.and(Sort.by("id").ascending());
        }

        return sort;
    }

}