package br.com.garcom.enums;

import br.com.foxinline.util.Utils;

/**
 *
 * @author laverson
 */
public enum TipoLogradouro {

    RUA(1, "Rua"),
    AVENIDA(2, "Avenida"),
    CONDOMINIO(3, "Condomínio"),
    POVOADO(4, "Povoado"),
    RODOVIA(5, "Rodovia"),
    ESTRADA(6, "Estrada"),
    APARTAMENTO(7, "Apartamento"),
    PRACA(8, "Praça"),
    VILA(9, "Vila"),
    PARQUE(10, "Parque"),
    FAZENDA(11, "Fazenda"),
    AEROPORTO(12, "Aeroporto"),
    VIADUTO(13, "Viaduto"),
    ALAMEDA(14, "Alameda"),
    BLOCO(15, "Bloco"),
    BECO(16, "Beco"),
    CAMINHO(17, "Caminho"),
    ESCADINHA(18, "Escadinha"),
    ESTACAO(19, "Estação"),
    FORTALEZA(20, "Fortaleza"),
    GALERIA(21, "Galeria"),
    LADEIRA(22, "Ladeira"),
    LARGO(23, "Largo"),
    PRAIA(24, "Praia"),
    QUADRA(25, "Quadra"),
    QUILÔMETRO(26, "Quilômetro"),
    QUINTA(27, "Quinta"),
    SUPER_QUADRA(28, "Super Quadra"),
    TRAVESSA(29, "Travessa"),
    BR(30, "BR"),
    VIA(100, "Via"),
    LOTEAMENTO(100, "Loteamento"),
    CONJUNTO(101, "Conjunto"),
    OUTROS(31, "Outros");
    private String nome;
    private Integer valor;

    public static TipoLogradouro getTipoLogradouro(String str) {
        if (Utils.isEmpty(str)) {
            return null;
        }
        
        if (str.equals("RUA")) {
            return RUA;
        }
        if (str.equals("ALAMEDA")) {
            return ALAMEDA;
        }
        if (str.equals("VIA")) {
            return VIA;
        }
        if (str.equals("TRAVESSA")) {
            return TRAVESSA;
        }
        if (str.equals("LOTEAMENTOTABAJ")) {
            return LOTEAMENTO;
        }
        if (str.equals("AVENIDA")) {
            return AVENIDA;
        }
        if (str.equals("BECO")) {
            return BECO;
        }
        if (str.equals("RODOVIA")) {
            return RODOVIA;
        }
        if (str.equals("ESTRADA")) {
            return ESTRADA;
        }
        if (str.equals("PRACA")) {
            return PRACA;
        }
        
        return null;
    }

    private TipoLogradouro(Integer valor, String nome) {
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
        return "TipoLogradouro{" + "nome=" + nome + ", valor=" + valor + '}';
    }
}
