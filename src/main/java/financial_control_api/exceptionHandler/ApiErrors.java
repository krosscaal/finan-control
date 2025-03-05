/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.exceptionHandler;

import java.util.List;

public class ApiErrors {
    private Integer status;
    private String dataHora;
    private String titulo;
    private List<Campo> campos;

    public ApiErrors(Integer status, String dataHora, String titulo) {
        this.status = status;
        this.dataHora = dataHora;
        this.titulo = titulo;
    }

    public ApiErrors(Integer status, String dataHora, String titulo, List<Campo> campos) {
        this.status = status;
        this.dataHora = dataHora;
        this.titulo = titulo;
        this.campos = campos;
    }
    public static class Campo {
        private String chave;
        private String valor;

        public Campo(String chave, String valor) {
            this.chave = chave;
            this.valor = valor;
        }

        public String getChave() {
            return chave;
        }

        public void setChave(String chave) {
            this.chave = chave;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }
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

    public List<Campo> getCampos() {
        return campos;
    }

    public void setCampos(List<Campo> campos) {
        this.campos = campos;
    }
}
