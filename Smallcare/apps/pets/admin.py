from django.contrib import admin
from .models import Animal, User, Event

admin.site.register(Animal.Animal)
admin.site.register(User.User)
admin.site.register(Event.Event)
# Register your models here.
