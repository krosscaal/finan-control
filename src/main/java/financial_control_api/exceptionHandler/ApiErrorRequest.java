/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.exceptionHandler;

public class ApiErrorRequest {
    private Integer status;
    private String dataHora;
    private String titulo;
    private String resumo;

    public ApiErrorRequest(Integer status, String dataHora, String titulo, String resumo) {
        this.status = status;
        this.dataHora = dataHora;
        this.titulo = titulo;
        this.resumo = resumo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }
}
