import java.awt.Color;
import java.awt.Graphics;

public class Rectangulo implements Figura{
	Color color;
	boolean relleno;
	int x1,y1,x2,y2;
	int anchura, altura;
	Rectangulo(int x1,int y1,int x2,int y2,Color c,boolean fill){
		this.x1=x1;
		this.x2=x2;
		this.y1=y1;
		this.y2=y2;
		color = c;
		relleno = fill;
		procesaPuntos();		
	}
	public void pinta(Graphics g){
		g.setColor(color);	

		System.out.println("ENTRA2");
		System.out.println(y1);
		System.out.println(x1);
		if(!relleno)
			g.drawRect(x1, y1, anchura, altura);		
		else
			g.fillRect(x1, y1, anchura, altura);	
	}
	
	private void procesaPuntos(){
		if(x1<x2 & y1<y2){
			anchura = x2-x1;
			altura = y2-y1;
		}
		else{
			if(x1<x2 & y1>y2){
				anchura = x2 - x1;
				altura = y1-y2;
				this.y1 = y1-altura;
			}
			else
				if(x1>x2 & y1<y2){
					anchura =x1-x2;
					altura = y2-y1;
					this.x1=x1-anchura;
				}
				else{
					anchura=x1-x2;
					altura=y1-y2;
					this.x1=x1-anchura;	
					this.y1=y1-altura;
				}
		}
		
	}
}