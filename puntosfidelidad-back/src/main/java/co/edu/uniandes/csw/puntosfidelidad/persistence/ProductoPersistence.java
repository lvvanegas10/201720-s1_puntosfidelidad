/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.puntosfidelidad.persistence;


import co.edu.uniandes.csw.puntosfidelidad.entities.ProductoEntity;
import co.edu.uniandes.csw.puntosfidelidad.entities.RestauranteEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author aa.yepes
 */
@Stateless
public class ProductoPersistence {
  

    @PersistenceContext(unitName = "puntosfidelidadPU")
    protected EntityManager em;
    
    /**
     *
     * @param entity objeto producto que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ProductoEntity create(ProductoEntity entity) {
        
        /* Note que hacemos uso de un método propio de EntityManager para persistir la bodega en la base de datos.
        Es similar a "INSERT INTO table_codigo (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(entity);
        
        return entity;
    }
    
     /**
     * Actualiza una producto.
     *
     * @param entity: la producto que viene con los nuevos cambios. Por ejemplo
     * el codigo pudo cambiar. En ese caso, se haria uso del método update.
     * @return un bodega con los cambios aplicados.
     */
    public ProductoEntity update(ProductoEntity entity) {
        
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la bodega con los cambios, esto es similar a 
        "UPDATE table_codigo SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        return em.merge(entity);
    }

    /**
     *
     * Borra una producto de la base de datos recibiendo como argumento el id
     * 
     *
     * @param id: id correspondiente a la compra a borrar.
     */
    public void delete(Long id) {
       
        // Se hace uso de mismo método que esta explicado en public bodegaEntity find(Long id) para obtener la bodega a borrar.
        ProductoEntity entity = em.find(ProductoEntity.class, id);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from bodegaEntity where id=id;" - "DELETE FROM table_codigo WHERE condition;" en SQL.*/
        em.remove(entity);
    }

    /**
     * Busca si hay algun producto con el id que se envía de argumento
     *
     * @param id: id correspondiente a la producto buscada.
     * @return un producto.
     */
    public ProductoEntity find(Long id) {
        
       
        return em.find(ProductoEntity.class, id);
    }

    /**
     * Devuelve todas las producto de la base de datos.
     *
     * @return una lista con todas las producto que encuentre en la base de
     * datos, "select u from bodegaEntity u" es como un "select * from
     * bodegaEntity;" - "SELECT * FROM table_codigo" en SQL.
     */
    public List<ProductoEntity> findAll() {
       
        // Se crea un query para buscar todas las producto en la base de datos.
        TypedQuery query = em.createQuery("select u from ProductoEntity u", ProductoEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de producto.
        return query.getResultList();
    }
    
    /**
     * 
     * @param id
     * @return restautrante asociado
     */
    public RestauranteEntity getRestaurante(Long id){
        ProductoEntity prod = em.find(ProductoEntity.class, id);
                
        return prod.getRestaurante();
    }

}