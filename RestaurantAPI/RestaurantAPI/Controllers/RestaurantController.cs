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

        public List<Review> Reviews { get; set; }

        public string PictureUrl { get; set; }
    }

    // Define the model for a restaurant review
    public class Review
    {
        public string Author { get; set; }
        public string Body { get; set; }

        [Range(1, 5, ErrorMessage = "Value must be between 1 and 5.")]
        public int Rating { get; set; }
    }

    // In-memory storage for restaurants
    private static readonly List<Restaurant> _restaurants = new List<Restaurant>
{
    new Restaurant
    {
        Id = 1,
        Name = "The Spicy Noodle",
        Type = "Chinese",
        Address = "123 Main St.",
        Phone = "555-1234",
        Description = "Delicious Chinese food with a focus on spicy noodles.",
        Stars = 4,
        Reviews = new List<Review>
        {
            new Review
            {
                Author = "John Doe",
                Body = "The noodles here are amazing!",
                Rating = 4
            },
            new Review
            {
                Author = "Jane Smith",
                Body = "The service was great but the food was a bit too spicy for me.",
                Rating = 3
            }
        },
        PictureUrl = ""

    },
    new Restaurant
    {
        Id = 2,
        Name = "Pizza Palace",
        Type = "Italian",
        Address = "456 Oak St.",
        Phone = "555-5678",
        Description = "Authentic Italian pizza made with fresh ingredients.",
        Stars = 5,
        Reviews = new List<Review>
        {
            new Review
            {
                Author = "Bob Johnson",
                Body = "This is the best pizza I've ever had!",
                Rating = 5
            }
        },
        PictureUrl = ""
    }
};

    [HttpGet]
    [SwaggerOperation (Summary = "Get all restaurants", 
        Description = "Retrieve all restaurants from the database", 
        OperationId = "GetAllRestaurants", 
        Tags = new[] { "RestaurantController" }
)]
    public ActionResult<List<Restaurant>> GetAllRestaurants()
    {
        // Return all restaurants in the database as JSON
        return Ok(_restaurants);
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
        var restaurant = _restaurants.Find(r => r.Id == id);
        if (restaurant == null)
        {
            return NotFound();
        }
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
        var restaurants = _restaurants.FindAll(r => r.Name.Contains(name));
        return Ok(restaurants);
    }

    // POST operation to add a review to a restaurant
    [HttpPost("{id}/reviews")]
    [SwaggerOperation(
        Summary = "Add a review to a restaurant",
        Description = "Add a review to a restaurant in the database by ID",
        OperationId = "AddReviewToRestaurant",
        Tags = new[] { "RestaurantController" }
    )]
    public ActionResult AddReviewToRestaurant(int id, [FromBody] Review review)
    {
        // Find the restaurant with the specified ID
        var restaurant = _restaurants.Find(r => r.Id == id);
        if (restaurant == null)
        {
            return NotFound();
        }

        // Add the review to the restaurant's list of reviews
        restaurant.Reviews.Add(review);

        // Return a 201 Created response with the URL of the new review
        var location = $"/api/restaurant/{id}/reviews/{restaurant.Reviews.Count}";
        return Created(location, null);
    }

    // GET operation to retrieve all reviews for a restaurant by ID or name
    [HttpGet("{idOrName}/reviews")]
    [SwaggerOperation(
        Summary = "Get all reviews for a restaurant by ID or name",
        Description = "Retrieve all reviews for a restaurant in the database by ID or name",
        OperationId = "GetAllReviewsForRestaurant",
        Tags = new[] { "RestaurantController" }
    )]
    public ActionResult<List<Review>> GetAllReviewsForRestaurant(string idOrName)
    {
        // Check if the ID or name matches a restaurant in the database
        var restaurant = _restaurants.Find(r => r.Id.ToString() == idOrName || r.Name.ToLower() == idOrName.ToLower());
        if (restaurant == null)
        {
            return NotFound();
        }

        // Return the list of reviews for the matching restaurant
        return Ok(restaurant.Reviews);
    }

}