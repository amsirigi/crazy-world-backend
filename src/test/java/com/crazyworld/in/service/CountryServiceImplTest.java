package com.crazyworld.in.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import com.crazyworld.in.dao.CountryRepository;
import com.crazyworld.in.dao.entity.CountryEntity;
import com.crazyworld.in.exception.CountryNotFoundException;
import com.crazyworld.in.model.CountryLanguagePojo;
import com.crazyworld.in.model.CountryPojo;
import com.crazyworld.in.util.Continent;

@SpringBootTest
class CountryServiceImplTest {
	

	    @Mock
	    CountryRepository countryRepository;

	    @InjectMocks
	    CountryServiceImpl countryService;

	    @Test
	    void testGetByCountryName() {
	      
	        String countryName = "India";
	        CountryEntity mockCountryEntity = new CountryEntity();
	        mockCountryEntity.setName("India");

	        
	        when(countryRepository.findByName(countryName)).thenReturn(mockCountryEntity);

	        
	        CountryPojo result = countryService.getByCountryName(countryName);

	        
	        assertNotNull(result);
	        assertEquals("India", result.getName());
	        verify(countryRepository, times(1)).findByName(countryName);
	    }

	    @Test
	    void testGetByCountryNameNotFound() {
	        
	        String countryName = "NonExistentCountry";

	        
	        when(countryRepository.findByName(countryName)).thenReturn(null);

	       
	        CountryNotFoundException exception = assertThrows(CountryNotFoundException.class, () -> {
	            countryService.getByCountryName(countryName);
	        });

	        assertEquals("Country Details Not Found for Country : NonExistentCountry", exception.getMessage());
	        verify(countryRepository, times(1)).findByName(countryName);
	    }
	
	
	
	



	@Test
	void testGetCountryWithHighestLifeExpectancy() {
		
        CountryEntity country1 = new CountryEntity();
        country1.setName("Country1");
        country1.setLifeExpectancy(new BigDecimal("75.5"));

        CountryEntity country2 = new CountryEntity();
        country2.setName("Country2");
        country2.setLifeExpectancy(new BigDecimal("80.2"));

        List<CountryEntity> countries = new ArrayList<>();
        countries.add(country1);
        countries.add(country2);

      
        when(countryRepository.findAll()).thenReturn(countries);

        
        CountryPojo result = countryService.getCountryWithHighestLifeExpectancy();

        
        assertNotNull(result);
        assertEquals("Country2", result.getName());
        assertEquals(new BigDecimal("80.2"), result.getLifeExpectancy());
        verify(countryRepository, times(1)).findAll();
	}

	@Test
	void testGetLanguagesByRegion() {
		
        String region = "Asia";
        List<String> languages = new ArrayList<>();
        languages.add("English");
        languages.add("Hindi");

        
        when(countryRepository.findLanguagesByRegion(region)).thenReturn(languages);

        
        List<CountryLanguagePojo> result = countryService.getLanguagesByRegion(region);

        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("English", result.get(0).getLanguage());
        assertEquals("Hindi", result.get(1).getLanguage());
        verify(countryRepository, times(1)).findLanguagesByRegion(region);
	}

	@Test
	void testGetDistinctGovernmentForms() {
		 
        List<String> distinctGovernmentForms = Arrays.asList("Republic", "Monarchy", "Democracy");

        
        when(countryRepository.findDistinctGovernmentForms()).thenReturn(distinctGovernmentForms);

       
        List<CountryPojo> result = countryService.getDistinctGovernmentForms();

        
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Republic", result.get(0).getGovernmentForm());
        assertEquals("Monarchy", result.get(1).getGovernmentForm());
        assertEquals("Democracy", result.get(2).getGovernmentForm());
        verify(countryRepository, times(1)).findDistinctGovernmentForms();
	}

	@Test
	void testGetTop10PopulatedCountries() {
		
        List<CountryEntity> top10CountriesData = Arrays.asList(
            new CountryEntity("USA", "United States", Continent.NORTH_AMERICA, "North America",
                    new BigDecimal("98335100"), (short) 1776, 331449281, new BigDecimal("78.8"),
                    new BigDecimal("19500000"), new BigDecimal("18624400"), "United States", "Federal Republic",
                    "Joe Biden", 1, "US", null, null),
            new CountryEntity("PHL", "Philippines", Continent.ASIA, "Southeast Asia", new BigDecimal(300000.00),
					(short) 1946, 109581078, new BigDecimal(71.20), new BigDecimal(149743.00),
					new BigDecimal(103219.00), "Pilipinas", "Republic", "Rodrigo Duterte", 766, "PH", null, null),
            new CountryEntity("ARG", "Argentina", Continent.SOUTH_AMERICA, "South America", new BigDecimal(2780400.00),
					(short) 1816, 45195777, new BigDecimal(76.70), new BigDecimal(3402385.00),
					new BigDecimal(3233105.00), "Argentina", "Federal Republic", "Alberto Fernández", 69, "AR", null,
					null),
            new CountryEntity("DEU", "Germany", Continent.EUROPE, "Central Europe", new BigDecimal(357022.00),
            	    (short) 1871, 83132799, new BigDecimal(81.40), new BigDecimal(4128282.00),
            	    new BigDecimal(3802359.00), "Federal Republic of Germany", "Federal Republic", "Olaf Scholz", 49, "DE", null, null),


            new	CountryEntity("CHN", "China", Continent.ASIA, "East Asia", new BigDecimal(9596961.00),
            	    (short) 1949, 1444216107, new BigDecimal(76.90), new BigDecimal(14166692.00),
            	    new BigDecimal(14693430.00), "People's Republic of China", "Socialist Republic", "Xi Jinping", 86, "CN", null, null),

            new	CountryEntity("IND", "India", Continent.ASIA, "South Asia", new BigDecimal(3287263.00),
            	    (short) 1947, 1393409038, new BigDecimal(69.70), new BigDecimal(2043415.00),
            	    new BigDecimal(2039078.00), "Republic of India", "Federal Republic", "Narendra Modi", 91, "IN", null, null),

            new	CountryEntity("BRA", "Brazil", Continent.SOUTH_AMERICA, "South America", new BigDecimal(8515767.00),
            	    (short) 1822, 213993437, new BigDecimal(75.70), new BigDecimal(3180210.00),
            	    new BigDecimal(3146414.00), "Federative Republic of Brazil", "Federal Republic", "Jair Bolsonaro", 55, "BR", null, null),

            new	CountryEntity("RUS", "Russia", Continent.EUROPE, "Eastern Europe", new BigDecimal(17098242.00),
            	    (short) 1991, 145912025, new BigDecimal(72.60), new BigDecimal(4166098.00),
            	    new BigDecimal(4601600.00), "Russian Federation", "Federal Republic", "Vladimir Putin", 7, "RU", null, null),

            new	CountryEntity("NGA", "Nigeria", Continent.AFRICA, "West Africa", new BigDecimal(923768.00),
            	    (short) 1960, 211400708, new BigDecimal(54.30), new BigDecimal(514005.00),
            	    new BigDecimal(468052.00), "Federal Republic of Nigeria", "Federal Republic", "Muhammadu Buhari", 234, "NG", null, null),

            new	CountryEntity("FRA", "France", Continent.EUROPE, "Western Europe", new BigDecimal(551695.00),
            	    (short) 843, 65273511, new BigDecimal(82.70), new BigDecimal(2962582.00),
            	    new BigDecimal(2789450.00), "French Republic", "Semi-Presidential Republic", "Emmanuel Macron", 33, "FR", null, null)

            
        );

       
        when(countryRepository.findTop10PopulatedCountries()).thenReturn(top10CountriesData);

        
        List<CountryPojo> result = countryService.getTop10PopulatedCountries();

       
        assertNotNull(result);
        assertEquals(10, result.size());
        assertEquals("United States", result.get(0).getName());
        assertEquals(331449281, result.get(0).getPopulation());
       

        verify(countryRepository, times(1)).findTop10PopulatedCountries();
	}

	@Test
	void testUpdateHeadOfState() {
		
        String countryName = "TestCountry";
        CountryEntity existingCountry = new CountryEntity();
        existingCountry.setName(countryName);

        
        String newHeadOfState = "NewHeadOfState";
        Map<String, Object> updates = new HashMap<>();
        updates.put("headOfState", newHeadOfState);

       
        when(countryRepository.findByName(countryName)).thenReturn(existingCountry);
        when(countryRepository.save(existingCountry)).thenReturn(existingCountry);

       
        CountryPojo updatedCountry = countryService.updateHeadOfState(countryName, updates);

        
        assertEquals(newHeadOfState, updatedCountry.getHeadOfState());
	}

	@Test
	void testGetCountriesWithLanguageCount() {
		  
        List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{"Country1", 5});
        mockData.add(new Object[]{"Country2", 3});

        
        when(countryRepository.findCountriesWithLanguageCount()).thenReturn(mockData);

        
        List<Object[]> result = countryService.getCountriesWithLanguageCount();

        
        verify(countryRepository, times(1)).findCountriesWithLanguageCount();
        assertEquals(2, result.size()); 
        assertEquals("Country1", result.get(0)[0]); 
        assertEquals(5, result.get(0)[1]);
        assertEquals("Country2", result.get(1)[0]);
        assertEquals(3, result.get(1)[1]);
    }
	}


