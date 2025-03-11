/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.exception;

import java.io.Serial;
import java.util.Locale;

public class BusinessException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessException(String mensagem) {
        super(mensagem.toUpperCase(Locale.ROOT));
    }
}
