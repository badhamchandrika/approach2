package ca.airmiles.travel.core.beans;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class ClusterTest {
    Cluster fixture;

    @BeforeEach
    void setUp()  {
       fixture = new Cluster("cluster name","package name", "redemption id","package id", "trip type");
    }

    @Test
    void test()  {
        assertEquals(fixture.getClusterName(),"cluster name");
        assertEquals(fixture.getPackageName(),"package name");
        assertEquals(fixture.getRedemption(),"redemption id");
        assertEquals(fixture.getPackageID(),"package id");
        assertEquals(fixture.getPackageID(),"package id");
        assertEquals(fixture.getTripType(),"trip type");

        fixture.setPackageID("another id");
        fixture.setPackageName("another p name");
        fixture.setRedemption("another r name");
        fixture.setClusterName("another c name");
        fixture.setTripType("another t name");
    }
}
