package cowboycheckers.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JOptionPane;

import cowboycheckers.usuarios.Jugador;
import cowboycheckers.vistas.VentanaPrincipal;

public class Tablero {

    // Fases de juego
    public static final int FASE_FIN_JUEGO = -1;
    public static final int FASE_COLOCACION = 0;
    public static final int FASE_MOVIMIENTO = 1;
    public static final int FASE_ELIMINACION = 2;

    // Array de letras
    public static final char[] ALFABETO = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X'};
    public static final String[] REFERENCIA_TABLA = {"0,0", "0,3", "0,6", "1,1", "1,3", "1,5", "2,2", "2,3", "2,4", "3,0", "3,1", "3,2", "3,4", "3,5", "3,6", "4,2", "4,3", "4,4", "5,1", "5,3", "5,5", "6,0", "6,3", "6,6"};

    // Class variables
    private ArrayList<Localizacion> lista_localizacion;
    private ArrayList<Arista> lista_arista;
    private JugarPieza[][] tablaArray;
    private VentanaPrincipal mw;
    private int fase_actual;
    private boolean modoTrampa;
    private boolean ignorarMensajes;


    /***
     * Este constructor recibe una instancia de otro tablero
     * y con esto duplica todos los valores de este a fin
     * de generar una nueva instancia con atributos identicos pero
     * que sean de diferentes referencias
     * @param tab
     */
    public Tablero(Tablero tab) {
        this.lista_localizacion = new ArrayList<Localizacion>();
        this.lista_arista = new ArrayList<Arista>();
        this.tablaArray = new JugarPieza[7][7];
        this.fase_actual = tab.fase_actual;
        this.modoTrampa = tab.modoTrampa;
        this.mw = tab.mw;

        copiarLocLista(tab);
        copiarAristaLista(tab);
        copiarTablaArray(tab);
    }

    /***
     * Constructor habitual del tablero, la ventana principal
     * sirve para actualizar los cambios del tablero en pantalla.
     * @param mw
     */
    public Tablero(VentanaPrincipal mw) {
        lista_localizacion = new ArrayList<Localizacion>();
        lista_arista = new ArrayList<Arista>();
        this.cargarTabla();

        Collections.sort(lista_localizacion);

        this.fase_actual = FASE_COLOCACION;
        this.tablaArray = newTabla();
        this.modoTrampa = false;
        this.mw = mw;
    }

    private void copiarTablaArray(Tablero tab) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                this.tablaArray[i][j] = new JugarPieza(tab.tablaArray[i][j]);
            }
        }
    }

    private void copiarAristaLista(Tablero tab) {
        for (Localizacion l : tab.lista_localizacion) {
            this.lista_localizacion.add(new Localizacion(l));
        }
    }

    private void copiarLocLista(Tablero tab) {
        for (Arista e : tab.lista_arista) {
            this.lista_arista.add(new Arista(e));
        }
    }

    /***
     * Esta función actualizará la tabla con las piezas en el.
     * Esta función tiene cuidado de las posiciones que tienen posición nulas.
     */
    public void updateTabla() {
        for (int i = 0; i < 24; i++) {
            String t[] = REFERENCIA_TABLA[i].split(",");
            int fila = Integer.parseInt(t[0]);
            int col = Integer.parseInt(t[1]);
            String etiqueta = String.valueOf(ALFABETO[i]);
            Localizacion loc = this.getLocacionByEtiqueta(etiqueta);
            if (loc.getPieza() != null)
                this.tablaArray[fila][col] = loc.getPieza();
            else
                this.tablaArray[fila][col] = new JugarPieza(new Jugador("", "red").getColor(), new Jugador("", "red"), -1);
        }
    }

    /***
     * Retornará el jugarPieza del tablero
     * @return
     */
    public JugarPieza[][] getTablaArray() {
        return this.tablaArray;
    }

    /***
     * Determinará el estado del juego actual y lo retornará
     *
     * @param curJugador
     * @return
     */
    public int getFaseActual(Jugador curJugador) {
        if (!curJugador.esHumano()) {
            this.ignorarMensajes = true;
        } else
            this.ignorarMensajes = false;
        if (curJugador.getPiezasJugadas() < 9)
            return FASE_COLOCACION;

        else if (fase_actual == FASE_ELIMINACION || fase_actual == FASE_FIN_JUEGO) {
            return fase_actual;
        } else
            return FASE_MOVIMIENTO;
    }

    /***
     * Activa el modo trampa.
     */
    public void setModoTrampa() {
        this.modoTrampa = true;
    }

    /***
     * Setea la fase actual en el tablero.
     * @param fase_actual
     */
    public void setFaseActual(int fase_actual) {
        this.fase_actual = fase_actual;
    }

    /***
     * Esta función retornará la localización que contenga la etqueta dada.
     * @param etiqueta
     * @return
     */
    public Localizacion getLocacionByEtiqueta(String etiqueta) {
        for (int i = 0; i < lista_localizacion.size(); i++)
            if (lista_localizacion.get(i).getEtiqueta().equals(etiqueta))
                return lista_localizacion.get(i);

        return null;
    }

    /***
     * Crea un tablero inicial para ser usada por la GUI.
     * Los lugares donde se pueden colocar fichas se setean con
     * una ID de -1 para que las piezas no se dibujen en el.
     *
     * Lugares invalidos del tablero se setean como null
     * @return
     */
    private JugarPieza[][] newTabla() {

        JugarPieza[][] tab = new JugarPieza[7][7];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++) {
                Jugador p = new Jugador("", "red");
                tab[i][j] = new JugarPieza(p.getColor(), p, -1);
            }
        ArrayList<ArrayList<Integer>> lugaresNoPosicionables = validarPosicion();
        int k = 0;
        for (ArrayList<Integer> i : lugaresNoPosicionables) {
            for (Integer j : i) {
                tab[k][j] = null;
            }
            k++;
        }

        return tab;
    }

    /***
     * Esta función harcodea las posiciones invalidad en el tablero.
     * @return
     */
    private ArrayList<ArrayList<Integer>> validarPosicion() {
        ArrayList<ArrayList<Integer>> p = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> fila0 = new ArrayList<Integer>(Arrays.asList(1, 2, 4, 5));
        ArrayList<Integer> fila1 = new ArrayList<Integer>(Arrays.asList(0, 2, 4, 6));
        ArrayList<Integer> fila2 = new ArrayList<Integer>(Arrays.asList(0, 1, 5, 6));
        ArrayList<Integer> fila3 = new ArrayList<Integer>(Arrays.asList(3));
        ArrayList<Integer> fila4 = new ArrayList<Integer>(Arrays.asList(0, 1, 5, 6));
        ArrayList<Integer> fila5 = new ArrayList<Integer>(Arrays.asList(0, 2, 4, 6));
        ArrayList<Integer> fila6 = new ArrayList<Integer>(Arrays.asList(1, 2, 4, 5));

        p.add(fila0);
        p.add(fila1);
        p.add(fila2);
        p.add(fila3);
        p.add(fila4);
        p.add(fila5);
        p.add(fila6);
        return p;
    }

    public boolean newMensajeError(String error, int delay) {
        if (this.ignorarMensajes && delay != 5000)
            return false;
        this.mw.getTableroJuego().setError(error, delay);
        this.mw.getTableroJuego().iniciarTimer();
        return false;
    }

    /***
     * Esta función intenta poner las piezas en determinada localización.
     * Retorna si se puede o no colocar dicha pieza ahí.
     * @param jugador
     * @param piezaID
     * @param etiquetaLoc
     * @return
     */
    public boolean lugarPieza(Jugador jugador, int piezaID, String etiquetaLoc) {
        JugarPieza curPieza = jugador.getPieza(piezaID);
        Localizacion nuevaLoc = getLocacionByEtiqueta(etiquetaLoc);

        if (curPieza == null || nuevaLoc == null) {
            return newMensajeError("Pieza o etiqueta de posicion invalida", 1500);
        }

        // Si la pieza seleccionada no ha sido colocada aún
        if (curPieza.getEstado() != JugarPieza.NOPOSICIONADO) {
            return newMensajeError("Pieza invalida - Ya ha sido colocada o esta muerta", 1500);
        }

        // Si la posición es vacia
        if (!nuevaLoc.contienePieza(null)) {
            return newMensajeError("Hay una pieza ahí", 1500);
        }

        nuevaLoc.setPieza(curPieza);
        curPieza.setEstado(JugarPieza.POSICIONADO);
        return true;

    }

    /***
     * Esta función intenta mover la pieza dentro del tablero
     *
     * verifica que el movimiento sea valido o no, retornando un booleano como respuesta
     * @param jugador
     * @param piezaID
     * @param etiquetaLoc
     * @return
     */
    public boolean moverPieza(Jugador jugador, int piezaID, String etiquetaLoc) {
        JugarPieza curPieza = jugador.getPieza(piezaID);
        Localizacion curLoc = getLocalizacionPieza(curPieza);
        Localizacion nuevaLoc = getLocacionByEtiqueta(etiquetaLoc);

        if (curPieza == null || nuevaLoc == null) {
            return newMensajeError("Pieza o etiqueta de posicion invalida", 1500);
        }

        // Detecta si esta en el modo trampa, si no verifica que sean adyacentes y que son vecinos
        if (!this.modoTrampa && jugador.getPuntuacion() > 3 && !esVecino(curLoc, nuevaLoc)) {
            return newMensajeError("Esa posicion no es adyacente", 1500);
        }

        // Verifica que la localización este vacia
        if (!nuevaLoc.contienePieza(null)) {
            return newMensajeError("Hay una pieza ahí", 1500);
        }

        curPieza.setEstado(JugarPieza.MOVIDO);
        curPieza.setMv(new Movimiento(curLoc, nuevaLoc));
        curPieza.setMoviendo(true);

        nuevaLoc.setPieza(curPieza);
        curLoc.setPieza(null);

        // Verifica si se ha formado un mill
        if (esMill(nuevaLoc)) {
            // hace que el siguiente jugador no continue y pasa a la fase eliminación
            String s = "%s a creado un mill!";
            s = String.format(s, jugador.getNombre());
            this.newMensajeError(s, 5000);
            this.setFaseActual(FASE_ELIMINACION);
            return false;
        }

        return true;

    }

    /***
     * Intenta remover la pieza del tablero, siempre que
     * sean oponentes y este sea posicion en esta localización
     *
     * Si se cumple retorna true, caso contrario false
     * @param jugador
     * @param piezaID
     * @param validador
     * @return
     */
    public boolean removerPieza(Jugador jugador, int piezaID, boolean validador) {
        JugarPieza curPieza = jugador.getPieza(piezaID);
        Localizacion curLoc = getLocalizacionPieza(curPieza);

        if (!validador)
            return newMensajeError("Tu no puedes remover tus propias piezas", 1500);


        if (curPieza == null) {
            return newMensajeError("Pieza invalidad - No se ha podido encontrar", 1500);
        }

        if (!curPieza.inPlay()) {
            return newMensajeError("Pieza invalida- No esta colocada o viva", 1500);

        }

        if (jugador.getPuntuacion() > 3 && esMill(curLoc)) {
            return newMensajeError("No puedes remover una pieza que hace un mill", 1500);

        }

        curLoc.setPieza(null);
        curPieza.setEstado(JugarPieza.MUERTO);
        int puntuacion = jugador.getPuntuacion();

        if (puntuacion == 3 && !this.modoTrampa) {
            String s = "%s ha activado el modo de vuelo con 3 piezas restantes";
            s = String.format(s, jugador.getNombre());
            JOptionPane.showMessageDialog(this.mw, s);
        }

        // Si al jugador le quedan menos de 3 piezas, se pasa a la fase de finalización del juego.
        // si no, se establece la fase a fase de movimiento.
        if (puntuacion < 3) {
            setFaseActual(FASE_FIN_JUEGO);

            // Devolveremos false para coronar al ganador
            // De lo contrario, nos quedaríamos en este reproductor, porque este método era
            // pasado al jugador opuesto del jugador actual para quitarle su pieza.
            return false;
        } else
            setFaseActual(FASE_MOVIMIENTO);

        return true;

    }

    /***
     * Esta función determina si se ha formado un mill o no, retornando true o false
     * @param loc
     * @return
     */
    public boolean esMill(Localizacion loc) {
        int contVert = contAdyacentes(loc, 0);
        int contHoriz = contAdyacentes(loc, 1);

        if (Math.max(contVert, contHoriz) > 2)
            return true;
        else
            return false;
    }

    /***
     * Contará el número de piezas adyacentes a la localización dada
     * @param loc
     * @param dir
     * @return
     */
    public int contAdyacentes(Localizacion loc, int dir) {
        Jugador owner = loc.getPieza().getOwner();
        int estado1, estado2;

        ArrayList<Localizacion> vecinos = someVecinos(loc, dir, owner);
        if (vecinos.size() == 2) {
            estado1 = vecinos.get(0).getPieza().getEstado();
            estado2 = vecinos.get(1).getPieza().getEstado();

        } else if (vecinos.size() == 1) {
            estado1 = vecinos.get(0).getPieza().getEstado();

            vecinos = someVecinos(vecinos.get(0), dir, owner);
            vecinos.remove(loc);

            if (vecinos.size() == 1) {
                estado2 = vecinos.get(0).getPieza().getEstado();
            } else
                return 0;
        } else
            return 0;

        if (estado1 == JugarPieza.MOVIDO || estado2 == JugarPieza.MOVIDO || loc.getPieza().getEstado() == JugarPieza.MOVIDO)
            return 3;
        else
            return 0;
    }

    /***
     * Determina si dos localizaciones son vecinas
     * @param loc1
     * @param loc2
     * @return
     */
    public boolean esVecino(Localizacion loc1, Localizacion loc2) {
        ArrayList<Localizacion> totalVecinos = allVecinos(loc1);
        if (totalVecinos.contains(loc2))
            return true;
        else
            return false;
    }

    /***
     *  Retorna las ubicaciones con direccion definida y un dueño que tambien es vecino
     */
    public ArrayList<Localizacion> someVecinos(Localizacion loc, int dir, Jugador owner) {
        ArrayList<Localizacion> vecinosLista = new ArrayList<Localizacion>();
        Arista curArista;
        Localizacion oppLoc;

        for (int i = 0; i < lista_arista.size(); i++) {
            curArista = lista_arista.get(i);
            oppLoc = curArista.getOpuesto(loc);

            if (curArista.tieneUbicacion(loc) && curArista.getAlineacion() == dir) {
                if (owner == null && oppLoc.getPieza() == null)
                    vecinosLista.add(curArista.getOpuesto(loc));
                else if (oppLoc.getPieza() != null && oppLoc.getPieza().getOwner() == owner)
                    vecinosLista.add(curArista.getOpuesto(loc));

            }
        }

        return vecinosLista;
    }

    /***
     * Retorna una lista de vecinos a la localización dada
     * @param loc
     * @return
     */
    public ArrayList<Localizacion> allVecinos(Localizacion loc) {
        ArrayList<Localizacion> lista_vecinos = new ArrayList<Localizacion>();
        for (int i = 0; i < lista_arista.size(); i++) {
            if (lista_arista.get(i).tieneUbicacion(loc))
                lista_vecinos.add(lista_arista.get(i).getOpuesto(loc));
        }

        return lista_vecinos;
    }

    /***
     * Retorna la localización de la pieza dada, caso no se encuentre devuelve null.
     * @param piece
     * @return
     */
    public Localizacion getLocalizacionPieza(JugarPieza piece) {
        for (int i = 0; i < lista_localizacion.size(); i++)
            if (lista_localizacion.get(i).contienePieza(piece))
                return lista_localizacion.get(i);
        return null;
    }

    // ----------------------------------------------------
    // ----------------------------------------------------

    /***
     * Añade una localización con la etiqueta dada
     * @param etiqueta
     * @return
     */
    private Localizacion addLocalizacion(String etiqueta) {
        Localizacion newLocalizacion = getLocacionByEtiqueta(etiqueta);

        if (newLocalizacion == null) {
            newLocalizacion = new Localizacion(etiqueta);
            this.lista_localizacion.add(newLocalizacion);
        }
        return newLocalizacion;
    }

    /***
     * Añade una arista entre las dos locaciones, con la etiqueta y alineación dada.
     * @param etiqueta
     * @param loc1
     * @param loc2
     * @param alineacion
     */
    private void addArista(String etiqueta, Localizacion loc1, Localizacion loc2, int alineacion) {
        Arista newArista = new Arista(etiqueta, loc1, loc2, alineacion);
        lista_arista.add(newArista);
    }

    /***
     * Carga la tabla leyendo un archivo .txt creando la lista de localizaciones y aristas
     */
    private void cargarTabla() {
        BufferedReader br = null;
        String curLine = "";
        String tokens[], locaciones[];
        Localizacion newLoc1, newLoc2;

        try {
            br = new BufferedReader(new FileReader("resources/board.txt"));
            while ((curLine = br.readLine()) != null) {
                // Split edge label from location labels
                tokens = curLine.split(":");

                locaciones = tokens[1].split(",");

                newLoc1 = addLocalizacion(locaciones[0]);
                newLoc2 = addLocalizacion(locaciones[1]);

                addArista(tokens[0], newLoc1, newLoc2, Integer.parseInt(tokens[2]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Cuenta el número de movimientos que tiene disponibles el jugador dado.
     * @param jugador
     * @return
     */
    public int numMovimientosDisponibles(Jugador jugador) {
        Localizacion curloc;
        int cont = 0;

        for (int i = 0; i < jugador.getPiezas().size(); i++) {
            curloc = getLocalizacionPieza(jugador.getPieza(i));
            if (curloc != null) {
                cont += someVecinos(curloc, 0, null).size();
                cont += someVecinos(curloc, 1, null).size();
            }
        }
        return cont;
    }

    public void setPieza(int r, int c, JugarPieza gp) {
        if (gp == null)
            this.tablaArray[r][c] = null;
        else
            this.tablaArray[r][c] = gp;
    }

    public boolean esModoVolar(Jugador p) {
        int score = p.getPuntuacion();

        if (score == 3 || this.modoTrampa) {
            return true;
        }
        return false;
    }

}
