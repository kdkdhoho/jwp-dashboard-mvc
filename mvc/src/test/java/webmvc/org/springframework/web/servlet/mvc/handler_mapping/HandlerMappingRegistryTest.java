package webmvc.org.springframework.web.servlet.mvc.handler_mapping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import webmvc.org.springframework.web.servlet.mvc.tobe.HandlerExecution;

@DisplayNameGeneration(ReplaceUnderscores.class)
class HandlerMappingRegistryTest {

    private HandlerMappingRegistry registry;

    @BeforeEach
    void setUp() {
        this.registry = new HandlerMappingRegistry(Set.of(
                new AnnotationHandlerMapping("samples"), new IndexHandlerMapping()
        ));
        registry.initialize();
    }

    @Test
    void RequestUri를_처리할_수_있는_컨트롤러_어노테이션_기반_핸들러를_가져온다() {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");

        // when
        Optional<Object> result = registry.getHandler(request);

        // then
        assertThat(result.get()).isInstanceOf(HandlerExecution.class);
    }

    @Test
    void 처리할_수_있는_핸들러가_없으면_예외가_발생한다() {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/nonono");
        when(request.getMethod()).thenReturn("PATCH");

        // expect
        assertThatThrownBy(() -> registry.getHandler(request))
                .hasMessage("요청 URI을 처리할 핸들러가 없습니다. 요청 URI=" + request.getRequestURI());
    }
}