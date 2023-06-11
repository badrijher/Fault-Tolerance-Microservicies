<h1>Despliegue de propuestas</h1>

Para realizar el despliegue correctamente hay que seguir una serie de pasos, que se le muestran a continuación. Deberá seguir todos los pasos para cada propuesta de forma individual:
<ol>
<li>En primer lugar, acceda a la carpeta de la propuesta que desea probar, en este manual se accederá a la primera propuesta (ESTRUCTURA NVM BASICA - ESCALADO).</li> 
<li>Acceda a cada microservicio individualmente desde la consola de comandos, por ejemplo, "cd bubblesort".</li>
<li>Una vez dentro ejecute el comando "mvn clean package" para que se genere el fichero JAR que se ejecutará posteriormente.</li>
<li>Deberá seguir los pasos 2 y 3 para cada servicio.</li>
<li>Una vez hecho ejecute el fichero JAR, que se encuentra en la carpeta target, que se encargará del despliegue.</li>
<li>Ejecute el siguiente comando dentro de cada carpeta target: "java -jar nombre-del-archivo.jar". Debe ejecutar el comando para cada una de las carpetas (ignórese la carpeta “.vscode”) que se encuentran dentro de la propuesta ya que ello permitirá desplegar todos lo microservicios para así comprobar el correcto funcionamiento del sistema.</li> 
<li>Una vez que haya hecho todo el proceso, si todo se ha ejecutado correctamente, entonces el sistema ya se encuentra desplegado y debe proceder a la lectura del manual de usuario para probar el sistema.</li>
<li>Si desea detener el despliegue, debe seguir los siguientes pasos:
  <ul>
    <li>Presione Ctrl + Shift + Esc para abrir el Administrador de tareas de Windows.</li>
    <li>En la pestaña "Procesos" o "Detalles", busque el proceso relacionado con la ejecución del JAR. Puede aparecer con el nombre de la aplicación o el nombre del archivo JAR. Haga clic derecho en el proceso y seleccione "Finalizar tarea" o "Finalizar proceso" para detener su ejecución.</li>
  </li>
  </ul>
</ol>
