public class Afiliacion {
    public enum Categoria {
        FAMILIAR, VOLUNTARIO_ADHERENTE,
    }

    public enum Subcategoria {
        CONYUGE, CONVIVIENTE, DESCENDIENTE_PRIMER_GRADO, BECARIO,
        AGENTE_UNSL_CON_LICENCIA,
    }

    private long id;
    private String nombre;
    private int edad;
    private Categoria categoria;
    private Subcategoria subcategoria;
}
