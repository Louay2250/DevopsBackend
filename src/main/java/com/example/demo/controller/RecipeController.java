package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Recipe;
import com.example.demo.model.User;
import com.example.demo.service.RecipeService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private UserService userService;
	@PutMapping("/{id}")
	public Recipe updateRecipe(@RequestBody Recipe recipe , @PathVariable Long id) throws Exception {
		
	
		Recipe updatedRecipe=recipeService.updateRecipe(recipe, id);
		return updatedRecipe;
		
	}
	
	
	@PutMapping("/{id}/like")
	public Recipe likeRecipe(@PathVariable Long id ,@RequestHeader("Authorization") String jwt) throws Exception {
		
		User user=userService.findUserByJwt(jwt);
		Recipe updatedRecipe=recipeService.likeRecipe(id,user);
		return updatedRecipe;
		
	}
	
	
	@PostMapping()
	public Recipe createRecipe(@RequestBody Recipe recipe ,@RequestHeader("Authorization") String jwt) throws Exception {
		
		User user=userService.findUserByJwt(jwt);
		Recipe createdRecipe=recipeService.createRecipe(recipe, user);
		return createdRecipe;
		
	}
	
	@GetMapping()
	public List<Recipe> getAllRecipe() throws Exception{
		List<Recipe> recipes=recipeService.findAllRecipe();
		return recipes;
	}
	
	@DeleteMapping("/{recipeId}")
	public ResponseEntity<Map<String, String>> deleteRecipe(@PathVariable Long recipeId) throws Exception {
	    recipeService.deleteRecipe(recipeId);
	    Map<String, String> response = new HashMap<>();
	    response.put("message", "Recipe deleted successfully");
	    return ResponseEntity.ok(response);
	}

	
	

}
