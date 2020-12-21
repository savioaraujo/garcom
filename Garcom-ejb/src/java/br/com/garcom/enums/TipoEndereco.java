package br.com.garcom.enums;

import br.com.foxinline.util.Utils;

/**
 *
 * @author laverson
 */
public enum TipoEndereco {

    CASA(67, "Casa"),
    APARTAMENTO(65, "Apartamento"),
    LOJA(15, "Loja"),
    SALA(17, "Sala/Conjunto"),
    TERRENO(71, "Terreno/fração"),
    GALPAO(31, "Galpão"),
    PREDIO(33, "Prédio Comercial"),
    SALAO_COMERCIAL(100, "Salão Comercial"),
    PREDIO_RESIDENCIAL(35, "Prédio Residencial"),
    FAZENDA_SITIO_CHACARA(69, "Fazenda/Sítio/Chácara"),
    LOTE(01, "Lote"),
    BENFEITORIA_MARINHA(02, "Benfeitoria em terreno de Marinha"),
    GLEBA(03, "Gleba"),
    GARAGEM(100, "Garagem"),
    QUITINETE(100, "Quitinete"),
    ANDAR(100, "Andar"),
    PAVIMENTO(100, "Pavimento"),
    OUTRO(89, "Outros");
   
    private String nome;
    private Integer valor;

    public static TipoEndereco getTipo(String str) {
        
        if (Utils.isEmpty(str)) {
            return null;
        }
        
        if (str.equals("SALA")) {
            return SALA;
        }
        if (str.equals("GARAGEM")) {
            return GARAGEM;
        }
        if (str.equals("PREDIO RESIDENCIAL")) {
            return PREDIO_RESIDENCIAL;
        }
        if (str.equals("QUITINETE")) {
            return QUITINETE;
        }
        if (str.equals("SALAO COMERCIAL")) {
            return SALAO_COMERCIAL;
        }
        if (str.equals("ANDAR")) {
            return ANDAR;
        }
        if (str.equals("PAVIMENTO")) {
            return PAVIMENTO;
        }
        if (str.equals("SOBRE LOJA")) {
            return LOJA;
        }
        if (str.equals("PREDIO")) {
            return PREDIO;
        }
        if (str.equals("LOJA")) {
            return LOJA;
        }
        if (str.equals("APARTAMENTO")) {
            return APARTAMENTO;
        }
        if (str.equals("CASA")) {
            return CASA;
        }
        if (str.equals("GALPAO")) {
            return GALPAO;
        }

        return null;


    }

    private TipoEndereco(Integer valor, String nome) {
        this.valor = valor;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Integer getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "TipoEndereco{" + "nome=" + nome + ", valor=" + valor + '}';
    }
}
