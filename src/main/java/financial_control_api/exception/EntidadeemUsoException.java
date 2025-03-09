/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.exception;

import java.io.Serial;

public class EntidadeemUsoException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public EntidadeemUsoException(String message) {
        super(message);
    }
}
