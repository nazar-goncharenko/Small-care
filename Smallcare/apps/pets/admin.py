from django.contrib import admin
from .models import Animal, User

admin.site.register(Animal.Animal)
admin.site.register(User.User)
# Register your models here.
