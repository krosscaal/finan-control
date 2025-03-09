/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.exception;

import java.io.Serial;
import java.util.Locale;

public class BusinnesException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public BusinnesException(String mensagem) {
        super(mensagem.toUpperCase(Locale.ROOT));
    }
}
