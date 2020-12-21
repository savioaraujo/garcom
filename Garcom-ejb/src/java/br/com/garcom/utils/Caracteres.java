package br.com.garcom.utils;

import br.com.foxinline.util.Utils;
import br.com.garcom.usuario.modelo.Usuario;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author elyjr
 */
public class Caracteres {

    public static String cpfMask = "###.###.###-##";
    public static String cnpjMask = "##.###.###/####-##";
    public static String cepMask = "##.###-###";
    public static String phoneMask = "(##) ####-####";
    public static String simplePhoneMask = "####-####";
    public static String newSimplePhoneMask = "#####-####";
    public static String newPhoneMask = "(##) #####-####";

    public static String adicionarMascaraPorNome(String nomeMascara, String valor) {
        nomeMascara = nomeMascara.toLowerCase();
        if (nomeMascara.equals("cpf")) {
            return adicionarMascara(valor, cpfMask);
        } else if (nomeMascara.equals("cnpj")) {
            return adicionarMascara(valor, cnpjMask);
        } else if (nomeMascara.equals("phone")) {
            return adicionarMascara(valor, valor.length() > 8 ? newSimplePhoneMask : simplePhoneMask);
        } else if (nomeMascara.equals("dddphone")) {
            return adicionarMascara(valor, valor.length() > 10 ? newPhoneMask : phoneMask);
        } else if (nomeMascara.equals("cep")) {
            return adicionarMascara(valor, cepMask);
        }
        return null;
    }

    public static String formataPaginaLivro(int numeroPaginaInicial, int quantidadeFolhas) {

        String paginaPesquisa = String.valueOf(numeroPaginaInicial);
        int ultimaPagina = numeroPaginaInicial;
        boolean verso = false;
        for (int i = 0; i < quantidadeFolhas; i++) {
            if (i % 2 == 0) {
                verso = true;
            } else {
                ultimaPagina++;
                verso = false;
            }
        }
        paginaPesquisa += " - " + ultimaPagina + (verso ? "V" : "");
        return paginaPesquisa;
    }

    public static String removecaracter(String s) {
        if (s != null) {
            s = s.replace(".", "").replace("-", "").replace("/", "").replace("-", "").replaceAll("\\.", "").replace("-", "").replace("/", "").replace("-", "").replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("(", "").replace(")", "");
        } else {
            s = "";
        }
        return s;
    }

    public static String removeCaracterAnexo(String s) {
        if (s != null) {
            s = s.replace(":", "").replace("-", "").replaceAll("[ãâàáä]", "a").replaceAll("[êèéëẽ]", "e").replaceAll("[îìíïĩ]", "i").replaceAll("[õôòóö]", "o").replaceAll("[ûúùüũ]", "u").replaceAll("[ÃÂÀÁÄ]", "A").replaceAll("[ÊÈÉËẼ]", "E").replaceAll("[ÎÌÍÏĨ]", "I").replaceAll("[ÕÔÒÓÖ]", "O").replaceAll("[ÛÙÚÜŨ]", "U").replace('ç', 'c').replace('Ç', 'C').replace('ñ', 'n').replace('Ñ', 'N').replaceAll("!", "").replace("/", "").replace("-", "").replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("(", "").replace(")", "");
        }
        return s;
    }

    public static String adicionarMascara(String string, String mask) {
        MaskFormatter maskFormatter;

        try {
            if (!Utils.isEmpty(string)) {
                string = string.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
                maskFormatter = new MaskFormatter(mask);
                maskFormatter.setValueContainsLiteralCharacters(false);
                return maskFormatter.valueToString(string);
            } else {
                return "";
            }
        } catch (ParseException ex) {
            return "";
        }
    }

    /**
     * Remove caracteres especiais e substitui letras acentuadas
     *
     * @param text
     * @return
     */
    public static String removerAcentuacao(String text) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("[ãâàáä]", "a").replaceAll("[êèéë]", "e").replaceAll("[îìíï]", "i").replaceAll("[õôòóö]", "o").replaceAll("[ûúùü]", "u").replaceAll("[ÃÂÀÁÄ]", "A").replaceAll("[ÊÈÉË]", "E").replaceAll("[ÎÌÍÏ]", "I").replaceAll("[ÕÔÒÓÖ]", "O").replaceAll("[ÛÙÚÜ]", "U").replace('ç', 'c').replace('Ç', 'C').replace('ñ', 'n').replace('Ñ', 'N');
    }

    /**
     * Remove caracteres especiais e substitui letras acentuadas
     *
     * @param text
     * @return
     */
    public static String removeCaracteresEspeciais(String text) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("-", "").replaceAll("[ãâàáä]", "a").replaceAll("[êèéë]", "e").replaceAll("[îìíï]", "i").replaceAll("[õôòóö]", "o").replaceAll("[ûúùü]", "u").replaceAll("[ÃÂÀÁÄ]", "A").replaceAll("[ÊÈÉË]", "E").replaceAll("[ÎÌÍÏ]", "I").replaceAll("[ÕÔÒÓÖ]", "O").replaceAll("[ÛÙÚÜ]", "U").replace('ç', 'c').replace('Ç', 'C').replace('ñ', 'n').replace('Ñ', 'N').replaceAll("!", "").replaceAll("\\[\\´\\`\\?!\\@\\#\\$\\%\\¨\\*", " ").replaceAll("\\(\\)\\=\\{\\}\\[\\]\\~\\^\\]", " ").replaceAll("[\\.\\;\\-\\_\\+\\'\\ª\\º\\:\\;\\/]", " ");
    }

    /**
     * Remove caracteres especiais e substitui letras acentuadas
     *
     * @param text
     * @return
     */
    public static String removeCaracteresEspeciaisXmlRpsEnvio(String text) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("[ãâàáä]", "a").replaceAll("[êèéë]", "e").replaceAll("[îìíï]", "i").replaceAll("[õôòóö]", "o").replaceAll("[ûúùü]", "u").replaceAll("[ÃÂÀÁÄ]", "A").replaceAll("[ÊÈÉË]", "E").replaceAll("[ÎÌÍÏ]", "I").replaceAll("[ÕÔÒÓÖ]", "O").replaceAll("[ÛÙÚÜ]", "U").replace('ç', 'c').replace('Ç', 'C').replace('ñ', 'n').replace('Ñ', 'N').replaceAll("\\´\\`\\?\\@\\#\\$\\%\\¨\\*", " ").replaceAll("\\(\\)\\[\\]\\~\\^", " ").replaceAll("[\\+\\ª\\º\\°]", " ");
    }

    public static String valorNumeroPorExtenso(int numero) {
        if (numero == 0) {
            return ("zero");
        }

        long inteiro = (long) Math.abs(numero); // parte inteira do valor
        double resto = numero - inteiro;       // parte fracionária do valor

        String vlrS = String.valueOf(inteiro);
        if (vlrS.length() > 15) {
            return ("Erro: valor superior a 999 trilhões.");
        }

        String s = "", saux, vlrP;
        String centavos = String.valueOf((int) Math.round(resto * 100));

        String[] unidade = {"", "um", "dois", "três", "quatro", "cinco",
            "seis", "sete", "oito", "nove", "dez", "onze",
            "doze", "treze", "quatorze", "quinze", "dezesseis",
            "dezessete", "dezoito", "dezenove"};
        String[] centena = {"", "cento", "duzentos", "trezentos",
            "quatrocentos", "quinhentos", "seiscentos",
            "setecentos", "oitocentos", "novecentos"};
        String[] dezena = {"", "", "vinte", "trinta", "quarenta", "cinquenta",
            "sessenta", "setenta", "oitenta", "noventa"};
        String[] qualificaS = {"", "mil", "milhão", "bilhão", "trilhão"};
        String[] qualificaP = {"", "mil", "milhões", "bilhões", "trilhões"};

// definindo o extenso da parte inteira do valor
        int n, unid, dez, cent, tam, i = 0;
        boolean umReal = false, tem = false;
        while (!vlrS.equals("0")) {
            tam = vlrS.length();
// retira do valor a 1a. parte, 2a. parte, por exemplo, para 123456789:
// 1a. parte = 789 (centena)
// 2a. parte = 456 (mil)
// 3a. parte = 123 (milhões)
            if (tam > 3) {
                vlrP = vlrS.substring(tam - 3, tam);
                vlrS = vlrS.substring(0, tam - 3);
            } else { // última parte do valor
                vlrP = vlrS;
                vlrS = "0";
            }
            if (!vlrP.equals("000")) {
                saux = "";
                if (vlrP.equals("100")) {
                    saux = "cem";
                } else {
                    n = Integer.parseInt(vlrP, 10);  // para n = 371, tem-se:
                    cent = n / 100;                  // cent = 3 (centena trezentos)
                    dez = (n % 100) / 10;            // dez  = 7 (dezena setenta)
                    unid = (n % 100) % 10;           // unid = 1 (unidade um)
                    if (cent != 0) {
                        saux = centena[cent];
                    }
                    if ((n % 100) <= 19) {
                        if (saux.length() != 0) {
                            saux = saux + " e " + unidade[n % 100];
                        } else {
                            saux = unidade[n % 100];
                        }
                    } else {
                        if (saux.length() != 0) {
                            saux = saux + " e " + dezena[dez];
                        } else {
                            saux = dezena[dez];
                        }
                        if (unid != 0) {
                            if (saux.length() != 0) {
                                saux = saux + " e " + unidade[unid];
                            } else {
                                saux = unidade[unid];
                            }
                        }
                    }
                }
                if (vlrP.equals("1") || vlrP.equals("001")) {
                    if (i == 0) // 1a. parte do valor (um real)
                    {
                        umReal = true;
                    } else {
                        saux = saux + " " + qualificaS[i];
                    }
                } else if (i != 0) {
                    saux = saux + " " + qualificaP[i];
                }
                if (s.length() != 0) {
                    s = saux + " e " + s;
                } else {
                    s = saux;
                }
            }
            if (((i == 0) || (i == 1)) && s.length() != 0) {
                tem = true; // tem centena ou mil no valor
            }
            i = i + 1; // próximo qualificador: 1- mil, 2- milhão, 3- bilhão, ...
        }

// definindo o extenso dos centavos do valor
        if (!centavos.equals("0")) { // valor com centavos
            if (s.length() != 0) // se não é valor somente com centavos
            {
                s = s + " e ";
            }
            if (centavos.equals("1")) {
                s = s + "um";
            } else {
                n = Integer.parseInt(centavos, 10);
                if (n <= 19) {
                    s = s + unidade[n];
                } else {             // para n = 37, tem-se:
                    unid = n % 10;   // unid = 37 % 10 = 7 (unidade sete)
                    dez = n / 10;    // dez  = 37 / 10 = 3 (dezena trinta)
                    s = s + dezena[dez];
                    if (unid != 0) {
                        s = s + " e " + unidade[unid];
                    }
                }
            }
        }
        return s;
    }

    public static String valorPorExtenso(double vlr) {
        if (vlr == 0) {
            return ("zero");
        }

        long inteiro = (long) Math.abs(vlr); // parte inteira do valor
        double resto = vlr - inteiro;       // parte fracionária do valor

        String vlrS = String.valueOf(inteiro);
        if (vlrS.length() > 15) {
            return ("Erro: valor superior a 999 trilhões.");
        }

        String s = "", saux, vlrP;
        String centavos = String.valueOf((int) Math.round(resto * 100));

        String[] unidade = {"", "um", "dois", "três", "quatro", "cinco",
            "seis", "sete", "oito", "nove", "dez", "onze",
            "doze", "treze", "quatorze", "quinze", "dezesseis",
            "dezessete", "dezoito", "dezenove"};
        String[] centena = {"", "cento", "duzentos", "trezentos",
            "quatrocentos", "quinhentos", "seiscentos",
            "setecentos", "oitocentos", "novecentos"};
        String[] dezena = {"", "", "vinte", "trinta", "quarenta", "cinquenta",
            "sessenta", "setenta", "oitenta", "noventa"};
        String[] qualificaS = {"", "mil", "milhão", "bilhão", "trilhão"};
        String[] qualificaP = {"", "mil", "milhões", "bilhões", "trilhões"};

// definindo o extenso da parte inteira do valor
        int n, unid, dez, cent, tam, i = 0;
        boolean umReal = false, tem = false;
        while (!vlrS.equals("0")) {
            tam = vlrS.length();
// retira do valor a 1a. parte, 2a. parte, por exemplo, para 123456789:
// 1a. parte = 789 (centena)
// 2a. parte = 456 (mil)
// 3a. parte = 123 (milhões)
            if (tam > 3) {
                vlrP = vlrS.substring(tam - 3, tam);
                vlrS = vlrS.substring(0, tam - 3);
            } else { // última parte do valor
                vlrP = vlrS;
                vlrS = "0";
            }
            if (!vlrP.equals("000")) {
                saux = "";
                if (vlrP.equals("100")) {
                    saux = "cem";
                } else {
                    n = Integer.parseInt(vlrP, 10);  // para n = 371, tem-se:
                    cent = n / 100;                  // cent = 3 (centena trezentos)
                    dez = (n % 100) / 10;            // dez  = 7 (dezena setenta)
                    unid = (n % 100) % 10;           // unid = 1 (unidade um)
                    if (cent != 0) {
                        saux = centena[cent];
                    }
                    if ((n % 100) <= 19) {
                        if (saux.length() != 0) {
                            saux = saux + " e " + unidade[n % 100];
                        } else {
                            saux = unidade[n % 100];
                        }
                    } else {
                        if (saux.length() != 0) {
                            saux = saux + " e " + dezena[dez];
                        } else {
                            saux = dezena[dez];
                        }
                        if (unid != 0) {
                            if (saux.length() != 0) {
                                saux = saux + " e " + unidade[unid];
                            } else {
                                saux = unidade[unid];
                            }
                        }
                    }
                }
                if (vlrP.equals("1") || vlrP.equals("001")) {
                    if (i == 0) // 1a. parte do valor (um real)
                    {
                        umReal = true;
                    } else {
                        saux = saux + " " + qualificaS[i];
                    }
                } else if (i != 0) {
                    saux = saux + " " + qualificaP[i];
                }
                if (s.length() != 0) {
                    s = saux + ", " + s;
                } else {
                    s = saux;
                }
            }
            if (((i == 0) || (i == 1)) && s.length() != 0) {
                tem = true; // tem centena ou mil no valor
            }
            i = i + 1; // próximo qualificador: 1- mil, 2- milhão, 3- bilhão, ...
        }

        if (s.length() != 0) {
            if (umReal) {
                s = s + " real";
            } else if (tem) {
                s = s + " reais";
            } else {
                s = s + " de reais";
            }
        }

// definindo o extenso dos centavos do valor
        if (!centavos.equals("0")) { // valor com centavos
            if (s.length() != 0) // se não é valor somente com centavos
            {
                s = s + " e ";
            }
            if (centavos.equals("1")) {
                s = s + "um centavo";
            } else {
                n = Integer.parseInt(centavos, 10);
                if (n <= 19) {
                    s = s + unidade[n];
                } else {             // para n = 37, tem-se:
                    unid = n % 10;   // unid = 37 % 10 = 7 (unidade sete)
                    dez = n / 10;    // dez  = 37 / 10 = 3 (dezena trinta)
                    s = s + dezena[dez];
                    if (unid != 0) {
                        s = s + " e " + unidade[unid];
                    }
                }
                s = s + " centavos";
            }
        }

        s = s.replaceAll("e  reais", "reais");
        s = s.replaceAll("e  centavos", "centavos");
        return (s);
    }

    public static String formataDataPorExtenso(Date vencimentoTitulo) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("PT", "BR"));

        return sdf.format(vencimentoTitulo);
    }

    public static String gerarCaracteres(char caracter, int tamanho) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < tamanho; j++) {
            builder.append(caracter);
        }
        return builder.toString();
    }

    public static String formataNumero(long numero, int tamanho) {
        return formataNumero(numero, tamanho, true);
    }

    public static String formataString(String string, int tamanho) {
        return formataString(string, tamanho, ' ', true);
    }

    public static String formataString(String string, int tamanho, char complemento, boolean alinhaAEsquerda) {
        if (string.length() < tamanho) {
            String c = String.valueOf(complemento);
            while (string.length() < tamanho) {
                if (alinhaAEsquerda) {
                    string += c;
                } else {
                    string = c + string;
                }
            }
        } else if (string.length() > tamanho) {
            int diferenca = string.length() - tamanho;
            if (alinhaAEsquerda) {
                string = string.substring(0, string.length() - diferenca);
            } else {
                string = string.substring(diferenca, string.length());
            }
        }

        return string;
    }

    public static String formataNumero(long numero, int tamanho, boolean zerosAEsquerda) {

        String string = String.valueOf(numero);

        while (string.length() < tamanho) {
            if (zerosAEsquerda) {
                string = "0".concat(string);
            } else {
                string += "0";
            }
        }

        return string;
    }

    public static String join(String[] parts, String separator) {
        StringBuilder sb = new StringBuilder();
        if (parts.length > 0) {
            sb.append(parts[0]);
            for (int i = 1; i < parts.length; ++i) {
                sb.append(separator).append(parts[i]);
            }
        }
        return sb.toString();
    }

    public static String join(String[] parts) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; ++i) {
            sb.append(parts[i]);
        }
        return sb.toString();
    }

    public static String toCamelCase(String strData, boolean keepBlanks) {
        String[] parts = strData.split("\\s+");
        for (int i = 0; i < parts.length; ++i) {
            if (parts[i].length() > 1) {
                parts[i] = Character.toUpperCase(parts[i].charAt(0)) + parts[i].substring(1).toLowerCase();
            } else {
                parts[i] = parts[i].toUpperCase();
            }
        }
        if (keepBlanks) {
            return join(parts, " ");
        } else {
            return join(parts);
        }
    }

    public static String removeCaracteresEspeciaispor(String text, String por) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("\\.", por).replaceAll(",", por).replaceAll("ç", por).replaceAll(" ", por).replaceAll("-", por).replaceAll("[ãâàáä]", por).replaceAll("[êèéë]", por).replaceAll("[îìíï]", por).replaceAll("[õôòóö]", por).replaceAll("[ûúùü]", por).replaceAll("[ÃÂÀÁÄ]", por).replaceAll("[ÊÈÉË]", por).replaceAll("[ÎÌÍÏ]", por).replaceAll("[ÕÔÒÓÖ]", por).replaceAll("[ÛÙÚÜ]", por).replaceAll("\\[\\´\\`\\?!\\@\\#\\$\\%\\¨\\*", por).replaceAll("\\(\\)\\=\\{\\}\\[\\]\\~\\^\\]", por).replaceAll("[\\.\\;\\-\\_\\+\\'\\ª\\º\\:\\;\\/]", por);
    }

    public static String replaceAcentos(String string) {

        string = string.replaceAll("\\|", "");
        string = string.replaceAll("Â", "&Acirc;");
        string = string.replaceAll("À", "&Agrave;");
        string = string.replaceAll("Á", "&Aacute;");
        string = string.replaceAll("Ä", "&Auml;");
        string = string.replaceAll("Ã", "&Atilde;");
        string = string.replaceAll("Ä", "&Auml;");
        string = string.replaceAll("Å", "&Aring;");

        string = string.replaceAll("â", "&acirc;");
        string = string.replaceAll("à", "&agrave;");
        string = string.replaceAll("á", "&aacute;");
        string = string.replaceAll("ä", "&auml;");
        string = string.replaceAll("ã", "&atilde;");
        string = string.replaceAll("ä", "&atilde;");
        string = string.replaceAll("å", "&aring;");

        string = string.replaceAll("Ê", "&Ecirc;");
        string = string.replaceAll("È", "&Egrave;");
        string = string.replaceAll("É", "&Eacute;");
        string = string.replaceAll("Ë", "&Euml;");

        string = string.replaceAll("ê", "&ecirc;");
        string = string.replaceAll("è", "&egrave;");
        string = string.replaceAll("é", "&eacute;");
        string = string.replaceAll("ë", "&euml;");

        string = string.replaceAll("Î", "&Icirc;");
        string = string.replaceAll("Í", "&Iacute;");
        string = string.replaceAll("Ì", "&Igrave;");
        string = string.replaceAll("Ï", "&Iuml;");

        string = string.replaceAll("î", "&icirc;");
        string = string.replaceAll("í", "&iacute;");
        string = string.replaceAll("ì", "&igrave;");
        string = string.replaceAll("ï", "&iuml;");

        string = string.replaceAll("Ô", "&Ocirc;");
        string = string.replaceAll("Õ", "&Otilde;");
        string = string.replaceAll("Ò", "&Ograve;");
        string = string.replaceAll("Ó", "&Oacute;");
        string = string.replaceAll("Ö", "&Ouml;");

        string = string.replaceAll("ô", "&ocirc;");
        string = string.replaceAll("õ", "&otilde;");
        string = string.replaceAll("ò", "&ograve;");
        string = string.replaceAll("ó", "&oacute;");
        string = string.replaceAll("ö", "&ouml;");

        string = string.replaceAll("Û", "&Ucirc;");
        string = string.replaceAll("Ù", "&Ugrave;");
        string = string.replaceAll("Ú", "&Uacute;");
        string = string.replaceAll("Ü", "&Uuml;");

        string = string.replaceAll("û", "&ucirc;");
        string = string.replaceAll("ù", "&ugrave;");
        string = string.replaceAll("ú", "&uacute;");
        string = string.replaceAll("ü", "&uuml;");

        string = string.replaceAll("Ç", "&Ccedil;");
        string = string.replaceAll("ç", "&ccedil;");

        string = string.replaceAll("ý", "&yacute;");
        string = string.replaceAll("Ý", "&Yacute;");
        string = string.replaceAll("ÿ", "&yuml;");
        string = string.replaceAll("Ÿ", "&Yuml;");

        string = string.replaceAll("ñ", "&ntilde;");
        string = string.replaceAll("Ñ", "&Ntilde;");

        string = string.replaceAll("º", "&ordm;");
        string = string.replaceAll("°", "&ordm;");
        string = string.replaceAll("ª", "&ordf;");
        string = string.replaceAll("¹", "&sup1;");
        string = string.replaceAll("²", "&sup2;");
        string = string.replaceAll("³", "&sup3;");
        string = string.replaceAll("§", "&sect;");
        string = string.replaceAll("€", "&euro;");
        string = string.replaceAll("®", "&reg");
        string = string.replaceAll("ø", "&oslash;");
        string = string.replaceAll("þ", "&thorn;");
        string = string.replaceAll("æ", "&aelig;");
        string = string.replaceAll("Æ", "&AElig;");
        string = string.replaceAll("ß", "&szlig;");
        string = string.replaceAll("ð", "&eth;");
        string = string.replaceAll("»", "&raquo;");
        string = string.replaceAll("«", "&laquo;");
        string = string.replaceAll("©", "&copy;");
        string = string.replaceAll("µ", "&micro;");
        string = string.replaceAll("¥", "&yen;");
        string = string.replaceAll("Ð", "&ETH;");
        string = string.replaceAll("¢", "&cent;");
        string = string.replaceAll("₣", "&#8355;");
        string = string.replaceAll("₢", "&#8354;");
        string = string.replaceAll("ˆ", "&circ;");
        string = string.replaceAll("˜", "&tilde;");
        string = string.replaceAll("¨", "&uml;");
        string = string.replaceAll("´", "&cute;");

        return string;
    }
    
    public static String formataValor(Double valor, boolean porExtenso) {
        String valorStr = NumberFormat.getCurrencyInstance(new Locale("PT", "BR")).format(valor);
        if (!porExtenso) {
            return valorStr;
        }
        return String.format("%s (%s)", valorStr, Caracteres.valorPorExtenso(valor).toUpperCase()).replaceAll("\\$", "&#36;").replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;").replaceAll("\\.", "&#46;");
    }
    
     public static String criptografarSenha(String senha) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bs;
            messageDigest.reset();
            bs = messageDigest.digest(senha.getBytes());

            for (int i = 0; i < bs.length; i++) {
                String hexVal = Integer.toHexString(0xFF & bs[i]);
                if (hexVal.length() == 1) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(hexVal);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stringBuilder.toString();
    }
}
