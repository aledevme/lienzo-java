import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.util.*;

public class Dibujo extends Frame {

   String texto;

   private Vector figs = new Vector();
    private Vector dibujo = new Vector();
   
   private List listaFiguras;

   private Checkbox  relleno;

   private List listaColores;

   //nuevos
   private List listaTamanoLetra;
   private List listaTipoLetra;

   private Button deshacer;

   private TextField campoDeTexto = new TextField(15);

   private Button rehacer;

   private HashMap informacion = new HashMap();

   private Lienzo lienzo;
   

   class Lienzo extends Panel{
	   
	  public Lienzo(){
		   super();
	   }

      public void paint(Graphics g){
         if (figs.size()>0)
         for (int i=0; i<figs.size(); i++)
         ( (Figura)figs.get(i) ).pinta(g);
      }      

      public void dibuja(int x1, int y1, int x2, int y2) {

	       Color color;
    	  
    	  //variable para obtener el tipo figura
          String tf =(String)informacion.get("TipoFigura");
    	  //variable para obtener el relleno
          String r = (String)informacion.get("Relleno");
    	  //variable para obtener el color seleccionado
          String c = (String)informacion.get("Color");

          //tamaño
          String tLetra = (String)informacion.get("tamanoLetra");

          String tipoLetra = (String)informacion.get("tipoLetra");
    	  
    	  if(tf != null & r != null & c != null){

              //el switch lee el color seleccionado
              switch (c){
                  case "Rojo":

                      //rojo
                      color=new Color(255,0,0);
                      break;
                  case "Verde":
                      //verde
                      color = new Color(0,255,0);
                      break;
                  case "Azul":
                      //azul
                      color = new Color(0,0,255);
                      break;
                  case "Naranja":
                      //naranja
                      color = new Color(255,127,0);
                      break;
                  case "Amarillo":
                      //naranja
                      color = new Color(255,237,0);
                    break;
                  case "Negro":
                      //negro
                      color = new Color(0,0,0);
                      break;
                  case "Marrón":
                      //marron
                      color = new Color(130,80,5);
                      break;
                  default:
                      throw new IllegalStateException("Unexpected value: " + c);
              }

              switch (tf){
                  case "Rectangulo":
                      figs.add(new Rectangulo(x1,y1,x2,y2,color,r.equals("si")));
                      break;
                  case "Elipse":
                      figs.add(new Elipse(x1,y1,x2,y2,color,r.equals("si")));
                      break;
                  case "Texto":
                      System.out.println(informacion.get("tamanoLetra"));
                      System.out.println(informacion.get("tipoLetra"));
                      figs.add(new Texto(campoDeTexto.getText(), tipoLetra ,Integer.parseInt(tLetra) ,x1,y1,x2,y2,color,r.equals("si")));
                      break;
                  default:
                      System.out.println("No se ha seleccionado una opcion valida");
              }
          }
    	 this.repaint();
      }
      
   }


   
   // Clase para tratar los eventos del tipo ItemEvent
   class TrataEventosItem implements ItemListener {
	public void itemStateChanged(ItemEvent e) {

	    if(e.getSource() instanceof Checkbox){
			informacion.remove("Relleno");
			if(e.getStateChange()== ItemEvent.SELECTED)
				informacion.put("Relleno","si");
			else
				informacion.put("Relleno","no");
		}
		else{
			List a =(List)e.getSource();

			String selec = a.getSelectedItem();

			if(a==listaColores){
                System.out.println("color");
				informacion.remove("Color");
				informacion.put("Color", selec);				
			}else if (a == listaTamanoLetra){
                System.out.println("aqui");
			    informacion.remove("tamanoLetra");
                informacion.put("tamanoLetra", selec);
            }
			else if(a == listaTipoLetra){
                informacion.remove("tipoLetra");
                informacion.put("tipoLetra", selec);
            }
			else{
                System.out.println("tipofigura");
				informacion.remove("TipoFigura");
				informacion.put("TipoFigura", selec);
			}
		}
		
	}
	   
   }

   // deshacer Clase para tratar los eventos del tipo MouseEvent sobre el boton
   class TrataEventosRatonBotonBoton extends MouseAdapter {
  		public void mouseClicked(MouseEvent e){
  			if(figs.size() >0){
  			    dibujo.add(figs.get(figs.size()-1));
  	  			figs.remove(figs.size()-1);
  	  			lienzo.repaint();
  	  		}
  		}	 

   }
    class TrataEventosRatonBotonRehacer extends MouseAdapter {
        public void mouseClicked(MouseEvent e){
            if(dibujo.size() >0){
                figs.add(dibujo.get(dibujo.size()-1));
                dibujo.remove(dibujo.size()-1);
                lienzo.repaint();
            }
        }

    }

   // Clase para tratar los eventos del tipo MouseEvent sobre el Lienzo
   // Se deben implementar mousePressed y mouseReleased
   class TrataEventosRatonBotonLienzo extends MouseAdapter { 
      //Atributos
	   private int x1,y1,x2,y2;	   
	   public void mousePressed(MouseEvent e){
		   x1=e.getX();
		   y1=e.getY();		   
	   }
	   public void 	mouseReleased(MouseEvent e){
		   x2=e.getX();
		   y2=e.getY();
		   lienzo.dibuja(x1, y1, x2, y2);
	   }
   }

    class TrataEventosRatonTexto extends MouseAdapter {
        //Atributos
        private int x1,y1,x2,y2;
        public void mousePressed(MouseEvent e){
            x1=e.getX();
            y1=e.getY();
        }
        public void 	mouseReleased(MouseEvent e){
            x2=e.getX();
            y2=e.getY();
            lienzo.dibuja(x1, y1, x2, y2);
        }
    }
   
   class TrataEventosCierre extends WindowAdapter{
       public void windowClosing(WindowEvent e){
           System.exit(0);
        }
   }
   // Constructor 
   public Dibujo() { 
      
      informacion.put("TipoFigura","Elipse");
      informacion.put("Color","Rojo");
      informacion.put("Relleno","no");
      informacion.put("tamanoLetra","10");
      informacion.put("tipoLetra","Ayuthaya");

   //Creacion del lienzo y de los paneles con los componentes
      //Gestor de organizaci�n de la ventana
      setLayout(new BorderLayout());
      
      //Paneles
      Panel panelTamanosLetras = new Panel();
      Panel panelTipoLetras = new Panel();
      Panel panelOpciones = new Panel();
      Panel panelFiguras = new Panel();
      Panel panelColores = new Panel();
      Panel panelTextfield = new Panel();

      //
      lienzo = new Lienzo();
      
      //Boton
      deshacer = new Button("Deshacer");
      rehacer = new Button("Rehacer");

      //Lista figuras
      listaFiguras = new List();
      listaFiguras.setMultipleMode(false);
      listaFiguras.add("Elipse");
      listaFiguras.add("Rectangulo");
      listaFiguras.add("Texto");

       //Lista colores
      listaColores = new List();
      listaColores.setMultipleMode(false);
      listaColores.add("Rojo");
      listaColores.add("Verde");
      listaColores.add("Azul");
      listaColores.add("Naranja");
      listaColores.add("Amarillo");
      listaColores.add("Negro");
      listaColores.add("Marrón");

      //lista tamaños de letra
      listaTamanoLetra = new List();
       listaTamanoLetra.setMultipleMode(false);
       listaTamanoLetra.add("10");
       listaTamanoLetra.add("12");
       listaTamanoLetra.add("14");
       listaTamanoLetra.add("16");
       listaTamanoLetra.add("18");
       listaTamanoLetra.add("20");
       listaTamanoLetra.add("22");
       listaTamanoLetra.add("26");

      //lista tipos de letra
       listaTipoLetra = new List();
       listaTipoLetra.setMultipleMode(false);
       listaTipoLetra.add("Ayuthaya");
       listaTipoLetra.add("Big Caslon");

      //Checkbox
      relleno = new Checkbox("Relleno");
      
      //Gestor de organizacion de los paneles
      panelFiguras.setLayout(new FlowLayout());
      panelFiguras.add(new Label("Tipo Figura"));
      panelFiguras.add(listaFiguras);

       panelTextfield.setLayout(new FlowLayout());
       panelTextfield.add(new Label("Escriba el texto:"));
       panelTextfield.add(campoDeTexto);


      //añade lista de colores al frame
      panelColores.setLayout(new FlowLayout());
      panelColores.add(new Label("Color"));
      panelColores.add(listaColores);

      //añadiendo lista de tamaño de letra al frame
      panelTamanosLetras.setLayout(new FlowLayout());
      panelTamanosLetras.add(new Label("Tamaño letra"));
       panelTamanosLetras.add(listaTamanoLetra);

       panelTipoLetras.setLayout(new FlowLayout());
       panelTipoLetras.add(new Label("Tipos de letra"));
       panelTipoLetras.add(listaTipoLetra);
      
      panelOpciones.setLayout(new GridLayout(8,1));
      panelOpciones.add(panelFiguras);
      panelOpciones.add(panelTextfield);
      panelOpciones.add(panelTamanosLetras);
      panelOpciones.add(panelTipoLetras);
      panelOpciones.add(relleno);
      panelOpciones.add(panelColores);
      panelOpciones.add(deshacer);
      panelOpciones.add(rehacer);
      
   //Registro de auditores de eventos
      TrataEventosItem tei = new TrataEventosItem();
      listaFiguras.addItemListener(tei);
      listaColores.addItemListener(tei);
      listaTamanoLetra.addItemListener(tei);
      listaTipoLetra.addItemListener(tei);
      relleno.addItemListener(tei);
       rehacer.addMouseListener(new TrataEventosRatonBotonRehacer());
       deshacer.addMouseListener(new TrataEventosRatonBotonBoton());
      lienzo.addMouseListener(new TrataEventosRatonBotonLienzo());

      this.addWindowListener(new TrataEventosCierre());
      //A�adimos a la ventana el Lienzo y el panel de opciones 
      this.add(lienzo,BorderLayout.CENTER);
      this.add(panelOpciones,BorderLayout.EAST);
      setSize(800,800);
      setVisible(true);      
   }

   public static void main(String[] args) {
      new Dibujo();
   }
}
