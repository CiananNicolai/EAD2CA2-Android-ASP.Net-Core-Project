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
        PictureUrl = "https://th-thumbnailer.cdn-si-edu.com/-rYjvlwsefTRbBU8QLUdJtzaWus=/fit-in/1600x0/https://tf-cmsv2-smithsonianmag-media.s3.amazonaws.com/filer/2f/df/2fdf811a-36d0-4de1-a695-476be549112c/istock-503692580.jpg"

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
        PictureUrl = "https://patch.com/img/cdn20/users/23562214/20220602/123840/styles/patch_image/public/pizza-palace-ditmars-closing-astoria___02123629220.jpg"
    },
    new Restaurant
    {
        Id = 3,
        Name = "Taco Libre",
        Type = "Mexican",
        Address = "455 Oak St.",
        Phone = "555-3563",
        Description = "Authentic Mexican with the best Gauc in the City!",
        Stars = 4,
        Reviews = new List<Review>
        {
            new Review
            {
                Author = "Bob Johnson",
                Body = "This is the best guac I've ever had!",
                Rating = 5
            }
        },
        PictureUrl = "https://i2-prod.dublinlive.ie/incoming/article22780294.ece/ALTERNATES/s1200c/0_Screenshot-2022-01-17T155435506.png"
    },
     new Restaurant
    {
        Id = 4,
        Name = "Banyi Japanese Dining",
        Type = "Japanese",
        Address = "4 Bedford Row",
        Phone = "555-3353",
        Description = "Traditional Decor and Menu, with an extensive range of sushi, plus European desserts!",
        Stars = 4,
        Reviews = new List<Review>
        {
            new Review
            {
                Author = "Johnson Bobson",
                Body = "This is the best Sushi I've ever had!",
                Rating = 5
            }
        },
        PictureUrl = "https://www.banyijapanesedining.com/uploads/6/8/0/4/6804967/__2688738_orig.jpg"
    },
    new Restaurant
    {
        Id = 5,
        Name = "Istanbul Kebab House",
        Type = "Turkish",
        Address = "15 Longroad Road",
        Phone = "555-3353",
        Description = "Food made in a deep fryer guaranteeded!",
        Stars = 5,
        Reviews = new List<Review>
        {
            new Review
            {
                Author = "Bobson Johnson",
                Body = "This is the best kebab I've ever had!",
                Rating = 5
            }
        },
        PictureUrl = "https://media-cdn.tripadvisor.com/media/photo-s/0f/27/27/87/istanbul-kebab-house.jpg"
    },
    new Restaurant
    {
        Id = 6,
        Name = "Mr. Mario's Takeaway",
        Type = "Chipper",
        Address = "6 Rathfarmam Road",
        Phone = "555-3333",
        Description = "Authentic Italian-Irish Chipper, from us to you!",
        Stars = 4,
        Reviews = new List<Review>
        {
            new Review
            {
                Author = "Jane Doe",
                Body = "Best food I've takenaway.",
                Rating = 4
            }
        },
        PictureUrl = "https://s3-media0.fl.yelpcdn.com/bphoto/Trw3exkALcn7e2KaHKpMpQ/348s.jpg"
    },
       new Restaurant
    {
        Id = 7,
        Name = "Mr. Luigi's Takeaway",
        Type = "Chipper",
        Address = "5 Rathfarmam Road",
        Phone = "555-4444",
        Description = "Authentic Irish-Italian Chipper, from us to you!",
        Stars = 4,
        Reviews = new List<Review>
        {
            new Review
            {
                Author = "Jane Doe",
                Body = "Second best food I've ever taken away.",
                Rating = 3
            }
        },
        PictureUrl = "https://media-cdn.tripadvisor.com/media/photo-s/05/f0/8b/c6/luigi-s.jpg"
    },
       new Restaurant
    {
        Id = 8,
        Name = "McGuinness Chipper",
        Type = "Chipper",
        Address = "22 Belgard Street",
        Phone = "555-6983",
        Description = "Est in 1953 we've been serving dublin for generations.",
        Stars = 5,
        Reviews = new List<Review>
        {
            new Review
            {
                Author = "Tom McGuinesss",
                Body = "Best takeaway in the city !!",
                Rating = 5
            }
        },
        PictureUrl = "https://lh5.googleusercontent.com/p/AF1QipNMfKVCY2n3YijuJSCTSLjudtB-3q6RlDWFhLbO=w408-h509-k-no"
    },
       new Restaurant
    {
        Id = 9,
        Name = "Milanos",
        Type = "Italian",
        Address = "East Essex Street",
        Phone = "555-3999",
        Description = "Large pizza menu with other italian dishes served in a stylish dining room with a modern feel.",
        Stars = 4,
        Reviews = new List<Review>
        {
            new Review
            {
                Author = "Ashwin Kothale",
                Body = "Lovely pizza, lovely service.",
                Rating = 4
            }
        },
        PictureUrl = "https://lh5.googleusercontent.com/p/AF1QipOgH06bSc1GX5hzIkJwjWnf2FfPC6kok5K1tuI=w408-h272-k-no"
    },
       new Restaurant
    {
        Id = 10,
        Name = "Hard Rock Cafe",
        Type = "American",
        Address = "12 Fleet Street",
        Phone = "555-3999",
        Description = "Rock n' Roll themed chain with high-energy vibe serving American classics.",
        Stars = 4,
        Reviews = new List<Review>
        {
            new Review
            {
                Author = "Mark Thornton",
                Body = "Ate here and was impressed.",
                Rating = 4
            }
        },
        PictureUrl = "https://lh5.googleusercontent.com/p/AF1QipNdS8v4oeeoTtwddV2DM3MjoYDYwuY4hAdQH02d=w408-h271-k-no"
    },
    new Restaurant
    {
        Id = 11,
        Name = "Lee's Charming Noodles",
        Type = "Chinese",
        Address = "105 Parnell Street",
        Phone = "555-2894",
        Description = "Relaxed and cosy Chinese restuarants serving traditional noodle dishes and stir-fries.",
        Stars = 5,
        Reviews = new List<Review>
        {
            new Review
            {
                Author = "Elijel de La Cruz",
                Body = "Empty when I was there, but everyone else was missing out!",
                Rating = 5
            }
        },
        PictureUrl = "https://www.google.com/maps/place/Lee's+Charming+Noodles/@53.3536788,-6.2585251,3a,75y,90t/data=!3m8!1e2!3m6!1sAF1QipNS2aWn1bnet3yv0jCowvmRRK2WuVezuRHt0UKP!2e10!3e12!6shttps:%2F%2Flh5.googleusercontent.com%2Fp%2FAF1QipNS2aWn1bnet3yv0jCowvmRRK2WuVezuRHt0UKP%3Dw152-h86-k-no!7i3471!8i1952!4m7!3m6!1s0x48670e864cd16bf1:0xc89614cb7b36a186!8m2!3d53.3536788!4d-6.2585251!10e5!16s%2Fg%2F1tdmzxz1#"
    },
    new Restaurant
    {
        Id = 12,
        Name = "Thai Baan Cafe",
        Type = "Thai",
        Address = "10 Abbey Street",
        Phone = "555-2894",
        Description = "Relaxed and cosy Thai Cafe, with all the wants and needs of the Thai experience.",
        Stars = 5,
        Reviews = new List<Review>
        {
            new Review
            {
                Author = "Kate Piotorwska",
                Body = "Amazing Thai Street food order and enjoy!",
                Rating = 5
            }
        },
        PictureUrl = "https://lh5.googleusercontent.com/p/AF1QipPYeC1Tyjhldyv4mvQ1qh8_w6Hh9GALMi3FIQvi=w408-h544-k-no"
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

    // Deletes a review from a restaurant
    [HttpDelete("restaurants/{id}/reviews/{reviewIndex}")]
    [SwaggerOperation(
        Summary = "Delete a review from a restaurant.",
        Description = "Deletes a review from a restaurant given the restaurant ID and the index of the review to be deleted.",
        OperationId = "DeleteReview"
    )]
    public ActionResult DeleteReview(
        [SwaggerParameter("The ID of the restaurant.", Required = true)] int id,
        [SwaggerParameter("The index of the review to be deleted.", Required = true)] int reviewIndex)
    {
        var restaurant = _restaurants.FirstOrDefault(r => r.Id == id);

        if (restaurant == null)
        {
            return NotFound();
        }

        if (reviewIndex >= restaurant.Reviews.Count)
        {
            return BadRequest("Review index out of range.");
        }

        restaurant.Reviews.RemoveAt(reviewIndex);

        return Ok();
    }

    // Searches for restaurants by type
    [HttpGet("restaurants")]
    [SwaggerOperation(
        Summary = "Search for restaurants by type.",
        Description = "Searches for restaurants based on their type.",
        OperationId = "SearchRestaurantsByType"
    )]
    [SwaggerResponse(200, "A list of restaurants.", typeof(IEnumerable<Restaurant>))]
    public ActionResult<IEnumerable<Restaurant>> SearchRestaurantsByType(
        [SwaggerParameter("The type of restaurant to search for.", Required = true)] string type)
    {
        var restaurants = _restaurants.Where(r => r.Type == type);

        if (!restaurants.Any())
        {
            return NotFound();
        }

        return Ok(restaurants);
    }

}