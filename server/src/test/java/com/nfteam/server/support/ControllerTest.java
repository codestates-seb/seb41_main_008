package com.nfteam.server.support;

import com.nfteam.server.auth.utils.JwtTokenizer;
import com.nfteam.server.config.SecurityConfiguration;
import com.nfteam.server.domain.cart.service.CartService;
import com.nfteam.server.redis.repository.RedisRepository;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;


@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtTokenizer.class,
        SecurityConfiguration.class})
public class ControllerTest {

    @MockBean
    protected RedisRepository redisRepository;
    @MockBean
    protected CartService cartService;
}
