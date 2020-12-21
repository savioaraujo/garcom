package br.com.garcom.enums.venda;

/**
 *
 * @author evaldosavio
 */
public enum StatusVenda {

    ABERTA("Abert@"),
    PAGO("Pag@"),
    CONVERTIDO("Convertid@"),
    AGUARDANDO_ENVIO("Aguardando envio"),
    SAIU_ENTREGA("Saiu para entrega"),
    ENTREGUE("Entregue"),
    CANCELADA("Cancelad@");
    private String descricao;

    private StatusVenda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public String feminino(){
        return this.descricao.replace("@", "a");
    }
    
    public String masculino(){
        return this.descricao.replace("@", "o");
    }
}
