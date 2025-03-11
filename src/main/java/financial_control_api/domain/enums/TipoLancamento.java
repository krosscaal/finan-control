/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package financial_control_api.domain.enums;

public enum TipoLancamento {

    RECEITA("Receita"),
    DESPESA("Despesa");

    private String desc;

    TipoLancamento(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
