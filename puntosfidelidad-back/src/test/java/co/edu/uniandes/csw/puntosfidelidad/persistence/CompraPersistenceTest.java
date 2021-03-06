/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.puntosfidelidad.persistence;

import co.edu.uniandes.csw.puntosfidelidad.entities.ClienteEntity;
import co.edu.uniandes.csw.puntosfidelidad.entities.CompraEntity;
import co.edu.uniandes.csw.puntosfidelidad.entities.ProductoCompraEntity;
import co.edu.uniandes.csw.puntosfidelidad.entities.SucursalEntity;
import co.edu.uniandes.csw.puntosfidelidad.entities.TarjetaPuntosEntity;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author aa.yepes
 */
@RunWith(Arquillian.class)
public class CompraPersistenceTest {

    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de XYZ, el descriptor de la base de
     * datos y el archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CompraEntity.class.getPackage())
                .addPackage(CompraPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Inyección de la dependencia a la clase RestaurantePersistence cuyos
     * métodos se van a probar.
     */
    @Inject
    private CompraPersistence persistence;

    @Inject
    private ProductoCompraPersistence persistenceProd;

    @Inject
    private SucursalPersistence persistenceSucur;

    @Inject
    private ClientePersistence persistenceCliente;

    @Inject
    private TarjetaPuntosPersistence persistenceTarjetaPuntos;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

    /**
     *
     */
    private List<CompraEntity> data = new ArrayList<CompraEntity>();

    public CompraPersistenceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void clearData() {
        em.createQuery("delete from CompraEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            CompraEntity entity = factory.manufacturePojo(CompraEntity.class);
            SucursalEntity sucur = factory.manufacturePojo(SucursalEntity.class);
            ClienteEntity client = factory.manufacturePojo(ClienteEntity.class);

            TarjetaPuntosEntity tarjeta = factory.manufacturePojo(TarjetaPuntosEntity.class);

            persistenceCliente.create(client);
            entity.setCliente(client);

            persistenceSucur.create(sucur);
            entity.setSucursal(sucur);

            persistenceTarjetaPuntos.create(tarjeta);
            entity.setTarjetaPuntos(tarjeta);

            List<ProductoCompraEntity> prods = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                ProductoCompraEntity prod = factory.manufacturePojo(ProductoCompraEntity.class);
                persistenceProd.create(prod);
                prods.add(prod);
            }
            entity.setProductos(prods);

            em.persist(entity);
            data.add(entity);
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class ProductoPersistence.
     */
    @Test
    public void testCreate() throws Exception {
        PodamFactory factory = new PodamFactoryImpl();
        CompraEntity newEntity = factory.manufacturePojo(CompraEntity.class);
        CompraEntity result = persistence.create(newEntity);

        Assert.assertNotNull(result);
        CompraEntity entity = em.find(CompraEntity.class, result.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }

    /**
     * Test of update method, of class ProductoPersistence.
     */
    @Test
    public void testUpdate() throws Exception {
        CompraEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CompraEntity newEntity = factory.manufacturePojo(CompraEntity.class);

        newEntity.setId(entity.getId());

        persistence.update(newEntity);

        CompraEntity resp = em.find(CompraEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());

    }

    /**
     * Test of delete method, of class ProductoPersistence.
     */
    @Test
    public void testDelete() throws Exception {
        CompraEntity entity = data.get(0);
        persistence.delete(entity.getId());
        CompraEntity deleted = em.find(CompraEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Test of find method, of class ProductoPersistence.
     */
    @Test
    public void testFind() throws Exception {
        CompraEntity entity = data.get(0);
        CompraEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }

    /**
     * Test of findAll method, of class ProductoPersistence.
     */
    @Test
    public void testFindAll() throws Exception {
        List<CompraEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CompraEntity ent : list) {
            boolean found = false;
            for (CompraEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Test of findAll method, of class ProductoPersistence.
     */
    @Test
    public void testGetSucursal() throws Exception {
        CompraEntity entity = data.get(0);
        SucursalEntity sucursal = data.get(0).getSucursal();
        SucursalEntity sucur = persistence.getSucursal(entity.getId());
        Assert.assertEquals(sucursal.getNombre(), sucur.getNombre());
    }

    /**
     * Test of findAll method, of class ProductoPersistence.
     */
    @Test
    public void testGetCliente() throws Exception {
        CompraEntity entity = data.get(0);
        ClienteEntity cliente = data.get(0).getCliente();
        ClienteEntity clien = persistence.getCliente(entity.getId());
        Assert.assertEquals(cliente.getNombre(), clien.getNombre());
    }

    /**
     * Test of findAll method, of class ProductoPersistence.
     */
    @Test
    public void testGetTarjetaPuntos() throws Exception {
        CompraEntity entity = data.get(0);
        TarjetaPuntosEntity tarjeta = data.get(0).getTarjetaPuntos();
        TarjetaPuntosEntity tar = persistence.getTarjetaPuntos(entity.getId());
        Assert.assertEquals(tarjeta.getId(), tar.getId());
    }

    /**
     * Test of findAll method, of class ProductoPersistence.
     */
    @Test
    public void testGetProductos() throws Exception {
        CompraEntity entity = data.get(0);

        Assert.assertEquals(persistence.gerProductos(entity.getId()).size(), entity.getProductos().size());
        Assert.assertEquals(persistence.gerProductos(entity.getId()).get(0).getId(), entity.getProductos().get(0).getId());
    }
}
