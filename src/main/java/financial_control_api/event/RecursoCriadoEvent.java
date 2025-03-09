/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.event;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

public class RecursoCriadoEvent extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = 1L;

    private HttpServletResponse response;
    private Long id;
    public RecursoCriadoEvent(Object source, HttpServletResponse response, Long id) {
        super(source);
        this.response = response;
        this.id = id;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Long getId() {
        return id;
    }
}
