package webmvc.org.springframework.web.servlet.mvc.handlermapping;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import webmvc.org.springframework.web.servlet.mvc.tobe.ForwardController;

public class DefaultHandlerMapping implements HandlerMapping {

    private final ForwardController controller = new ForwardController("/index.jsp");

    @Override
    public void initialize() {
    }

    @Override
    public Optional<Object> getHandler(HttpServletRequest request) {
        if (request.getRequestURI().equals("/")) {
            return Optional.of(controller);
        }
        return Optional.empty();
    }
}