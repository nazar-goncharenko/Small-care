from django.http import response, HttpResponse
from django.shortcuts import render
from .models import Animal


def index(request):
    return HttpResponse("Hello, my sweaty Pets!")


def test(request):
    return HttpResponse("Test Page")


def my(request):
    list_of_pets = Animal.Animal.objects.all()
    return render(request, 'base.html', {'list_of_pets': list_of_pets})
