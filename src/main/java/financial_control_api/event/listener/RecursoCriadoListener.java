/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.event.listener;

import financial_control_api.event.RecursoCriadoEvent;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {
    @Override
    public void onApplicationEvent(RecursoCriadoEvent event) {
        HttpServletResponse response = event.getResponse();
        Long id = event.getId();
        addHeaderLocation(id, response);
    }

    private static void addHeaderLocation(Long id, HttpServletResponse response) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
