package br.com.caelum.carangobom.config.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams({
	@ApiImplicitParam(name = "username", dataType = "string", paramType = "query",
            value = "Filter by Username."),
    @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
    @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
            value = "Number of records per page."),
    @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
            value = "Sorting criteria in the format: property(,asc|desc). " +
                    "Default sort order is ascending. " +
                    "Multiple sort criteria are supported.")
})
public @interface UserFilterPageable {
}
