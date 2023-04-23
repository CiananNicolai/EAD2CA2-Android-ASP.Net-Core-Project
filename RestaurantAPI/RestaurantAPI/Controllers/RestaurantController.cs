using Microsoft.AspNetCore.Mvc;
using Swashbuckle.AspNetCore.Annotations;
using System.ComponentModel.DataAnnotations;

[Route("api/[controller]")]
[ApiController]
public class RestaurantController : ControllerBase
{
    // Define the model for a restaurant
    public class Restaurant
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Type { get; set; }
        public string Address { get; set; }
        public string Phone { get; set; }
        public string Description { get; set; }

        [Range(1, 5, ErrorMessage = "Value must be between 1 and 5.")]
        public int Stars { get; set; }
    }

    // GET operation to retrieve a restaurant by ID
    [HttpGet("{id}")]
    [SwaggerOperation(
        Summary = "Get a restaurant by ID",
        Description = "Retrieve a restaurant from the database by ID",
        OperationId = "GetRestaurantById",
        Tags = new[] { "RestaurantController" }
    )]
    public ActionResult<Restaurant> GetRestaurantById(int id)
    {
        // Retrieve a restaurant from the database and return it as JSON
        var restaurant = new Restaurant { Id = id, Name = "Example Restaurant", Address = "123 Main St", Phone = "555-555-5555", MenuItems = new[] { "Burger", "Fries", "Soda" } };
        return Ok(restaurant);
    }

    // GET operation to search for restaurants by name
    [HttpGet("search")]
    [SwaggerOperation(
        Summary = "Search for restaurants by name",
        Description = "Search for restaurants in the database by name",
        OperationId = "SearchRestaurantsByName",
        Tags = new[] { "RestaurantController" }
    )]
    public ActionResult<Restaurant[]> SearchRestaurantsByName([FromQuery] string name)
    {
        // Search for restaurants in the database and return them as JSON
        var restaurants = new Restaurant[] { new Restaurant { Id = 1, Name = name, Address = "123 Main St", Phone = "555-555-5555", MenuItems = new[] { "Burger", "Fries", "Soda" } } };
        return Ok(restaurants);
    }
}