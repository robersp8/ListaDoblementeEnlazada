public class ListaDoblementeEnlazada { 
    Nodo inicio; //inicio de la lista
  
    /* Nodo de la Lista Doblemente Enlazada*/
    class Nodo { 
        int datos; 
        Nodo anterior; 
        Nodo siguiente; 
  
        // Constructor del nuevo Nodo, anterior y siguiente serán creados por defecto a null
        Nodo(int d) { datos = d; } 
    } 
  
    // Añadimos un nodo al principio de la lista
    public void aniadir(int NuevoDato) 
    { 
        Nodo nuevoNodo = new Nodo(NuevoDato); 
  
        /* Seteamos siguiente del nuevo nodo como el inicio y anterior como null */
        nuevoNodo.siguiente = inicio; 
        nuevoNodo.anterior = null; 
  
        /* Cambiamos el anterior del Nodo inicial por el del nuevo */
        if (inicio != null) 
            inicio.anterior = nuevoNodo; 
  
        /* Cambiamos el nodo de inicio por el nuevo Nuevo Nodo creado */
        inicio = nuevoNodo; 
    } 
  
    /* Añadido de un nodo justo después de otro Nodo dado*/
    public void AniadirDespues(Nodo anterior_Nodo, int NuevoDato) 
    { 
  
        /*Comprobamos que el nodo anterior no sea nulo */
        if (anterior_Nodo == null) { 
            System.out.println("El Nodo no puede ser nulo"); 
            return; 
        } 

        Nodo nuevoNodo = new Nodo(NuevoDato); 
  
        /* Hacemos que el siguiente del nuevo nodo sea el siguiente del anterior nodo*/
        nuevoNodo.siguiente = anterior_Nodo.siguiente; 
  
        /* Hacemos que el nodo siguiente del nodo anterior sea el nuevo nodo*/
        anterior_Nodo.siguiente = nuevoNodo; 
  
        /* Hacemos que el anterior nodo del Nodo que hemos creado sea el Nodo anterior*/
        nuevoNodo.anterior = anterior_Nodo; 
  
        /**/
        if (nuevoNodo.siguiente != null) 
            nuevoNodo.siguiente.anterior = nuevoNodo; 
    } 
  
    /* Añadimos un nodo al final de la lista*/
    void aniadirFinal(int NuevoDato) 
    { 
        Nodo nuevoNodo = new Nodo(NuevoDato); 
  
        Nodo ultimo = inicio;
  
        /* Como este nodo va a ser el ultimo, hacemos que el siguiente nodo sea null*/
        nuevoNodo.siguiente = null; 
  
        /* Si la lista está vacia y no tiene nada al inicio, haremos que este nuevo nodo 
         * sea el primero*/
        if (inicio == null) { 
            nuevoNodo.anterior = null; 
            inicio = nuevoNodo; 
            return; 
        } 

        while (ultimo.siguiente != null) 
            ultimo = ultimo.siguiente; 
  
        /* Cambiamos el siguiente del ultimo nodo por el nuevo nodo */
        ultimo.siguiente = nuevoNodo; 
  
        /* Hacemos que el nodo anterior al nuevo nodo sea el que era el ultimo previamente */
        nuevoNodo.anterior = ultimo; 
    } 
  
    /* Imprimimos la lista de nodos empezando por un nodo dado*/
    public void imprimirLista(Nodo Nodo) 
    { 
        Nodo ultimo = null; 
        System.out.println("Recorriendo en direccion ascendente"); 
        while (Nodo != null) { 
            System.out.print(Nodo.datos + " "); 
            ultimo = Nodo; 
            Nodo = Nodo.siguiente; 
        } 
        System.out.println(); 
        System.out.println("Recorriendo en direccion descendente"); 
        while (ultimo != null) { 
            System.out.print(ultimo.datos + " "); 
            ultimo = ultimo.anterior; 
        } 
    } 
  
    /* Ejecuciones para probar las funciones creadas para manejo de la lista*/
    public static void main(String[] args) 
    { 
        // Lista vacia
        ListaDoblementeEnlazada ListaDoblementeEnlazada = new ListaDoblementeEnlazada(); 
  
        // Insertamos un 3 [3->NULL]
        ListaDoblementeEnlazada.aniadirFinal(3); 
  
        // Insertamos un 2 al principio [2->3->NULL]
        ListaDoblementeEnlazada.aniadir(2); 
  
        // Insertamos un 6 al principio también 6->2->3->NULL 
        ListaDoblementeEnlazada.aniadir(6); 
  
        // Insertamos un 9 al final [6->2->3->9->NULL]
        ListaDoblementeEnlazada.aniadirFinal(9); 
  
        // Insertamos un 5 después del 2 [6->2->5->3->9->NULL]
        ListaDoblementeEnlazada.AniadirDespues(ListaDoblementeEnlazada.inicio.siguiente, 5); 
  
        System.out.println("La ListaDoblementeEnlazada es: "); 
        ListaDoblementeEnlazada.imprimirLista(ListaDoblementeEnlazada.inicio); 
    } 
}