/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.exception;

import java.io.Serial;
import java.util.Locale;

public class EntidadeNaoEncontradaException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException(String message) {
        super(message.toUpperCase(Locale.ROOT));
    }
}
